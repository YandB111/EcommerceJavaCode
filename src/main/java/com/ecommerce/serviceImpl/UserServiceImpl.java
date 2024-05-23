package com.ecommerce.serviceImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecommerce.Repo.UserRepository;
import com.ecommerce.dao.RegisterationDTO;
import com.ecommerce.entity.LoginDTO;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	private final Map<String, String> lastSentOtpMap = new ConcurrentHashMap<>();

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private void scheduleTaskToDeleteUserData(User user, int delayInHours) {
	    scheduler.schedule(() -> {
	        // Check if the user is still not verified
	        if (user.getOtp() != null) {
	            // Delete the user data
	            userRepository.delete(user);
	            System.out.println("User data deleted for email: " + user.getEmail());
	        }
	    }, delayInHours, TimeUnit.HOURS);
	}

	
	// login service

	@Override
	public ResponseEntity<String> loginUser(LoginDTO loginDTO) {
		String email = loginDTO.getEmail();
		String password = loginDTO.getPassword();

		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			// Verify password
			if (user.isPasswordCorrect(password)) {
				// Password is correct, generate and return a success message or a token
				return ResponseEntity.ok("Login successful. Welcome to Y & B Ecommerce!");
			} else {
				// Password is incorrect
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Password Or Email. Login failed.");
			}
		} else {
			// User not found
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found. Login failed.");
		}
	}

	@Override
	public void registerUser(RegisterationDTO registrationDTO) {
	    Optional<User> optionalUser = userRepository.findByEmail(registrationDTO.getEmail());
	    LocalDate currentDate = LocalDate.now();
	    if (optionalUser.isPresent()) {
	        // If the user with the given email already exists, update the existing user
	        User existingUser = optionalUser.get();
	        existingUser.setName(registrationDTO.getName());
	        existingUser.setDob(registrationDTO.getDob());
	        existingUser.setAge(calculateAge(registrationDTO.getDob()));
	        existingUser.setRole(registrationDTO.getRole());
	        // No need to set createDate here, it will be automatically managed by Hibernate
	        existingUser.setCreateDate(new Date()); // set the createDateTime


	        
	        // Generate and set a new OTP
	        String otp = generateOTP();
	        existingUser.setOtp(otp);

	        // Set and encode the new password if provided
	        if (registrationDTO.getPassword() != null && !registrationDTO.getPassword().isEmpty()) {
	            existingUser.setPasswordAndEncode(registrationDTO.getPassword());
	        }

	        // Save the updated user
	        userRepository.save(existingUser);

	        // Send the new OTP to the user's email
	        sendOTPEmail(existingUser.getEmail(), otp);
	        lastSentOtpMap.put(existingUser.getEmail(), otp);
	        // Reschedule the task to delete user data after 1 day
	        scheduleTaskToDeleteUserData(existingUser, 3); // 3 hours delay for existing users

	    } else {
	        // If the user with the given email does not exist, create a new user
	        User newUser = new User();
	        newUser.setName(registrationDTO.getName());
	        newUser.setEmail(registrationDTO.getEmail());
	        newUser.setDob(registrationDTO.getDob());
	        newUser.setAge(calculateAge(registrationDTO.getDob()));
	        newUser.setRole(registrationDTO.getRole());
	        // No need to set createDate here, it will be automatically managed by Hibernate

	        newUser.setCreateDate(new Date()); // set the createDateTime


	        // Generate and set a new OTP
	        String otp = generateOTP();
	        newUser.setOtp(otp);

	        // Set and encode the new password if provided
	        if (registrationDTO.getPassword() != null && !registrationDTO.getPassword().isEmpty()) {
	            newUser.setPasswordAndEncode(registrationDTO.getPassword());
	        }

	        // Save the new user
	        userRepository.save(newUser);

	        // Send the new OTP to the user's email
	        sendOTPEmail(newUser.getEmail(), otp);

	        // Update the last sent OTP map
	        lastSentOtpMap.put(newUser.getEmail(), otp);

	        // Schedule the task to delete user data after 1 day
	        scheduleTaskToDeleteUserData(newUser, 3); // 3 hours delay for new users



	    }
	}

	

	private int calculateAge(LocalDate dob) {
		if (dob != null) {
			LocalDate currentDate = LocalDate.now();
			return Period.between(dob, currentDate).getYears();
		} else {
			throw new IllegalArgumentException("Date of birth cannot be null.");
		}
	}

	private String generateOTP() {
		Random random = new Random();

		int otpNumber = 100_000 + random.nextInt(900_000);
		return String.valueOf(otpNumber);
	}

	private void sendOTPEmail(String toEmail, String otp) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("yellowblack3266@gmail.com");
		message.setTo(toEmail);
		message.setSubject("OTP for Registration");
		message.setText("Your OTP Y & B Ecommerce  registration is: " + otp);

		javaMailSender.send(message);

		System.out.println("Email sent successfully to: " + toEmail);
	}

	// otp verficaiton done

	@Override
	public boolean verifyOTP(String email, String enteredOTP) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			String lastSentOtp = lastSentOtpMap.get(email);

			if (lastSentOtp != null && enteredOTP.equals(lastSentOtp)) {
				// Clear the last sent OTP
				lastSentOtpMap.remove(email);

				// Clear the user's OTP and save
				user.setOtp(null);
				userRepository.save(user);

				// Send a confirmation email to the user
				sendConfirmationEmail(user.getEmail());

				return true;
			}
		}

		return false;
	}

	private void sendConfirmationEmail(String toEmail) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("yellowblack3266@gmail.com");
		message.setTo(toEmail);
		message.setSubject("OTP Verification Successful");
		message.setText(
				"Your OTP for registration has been successfully verified. Welcome Explore More Products In Y & B ");

		javaMailSender.send(message);

		System.out.println("Confirmation email sent successfully to: " + toEmail);
	}

	@Override
	public ResponseEntity<?> getUserById(Long id) {
	    Optional<User> optionalUser = userRepository.findById(id);

	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
	    }
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		return optionalUser.orElse(null); // Returns null if the user is not found
	}

}
