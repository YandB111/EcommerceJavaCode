import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "../components/verify.css";

const VerifyOtp = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [enteredOTP, setEnteredOTP] = useState(['', '', '', '', '', '']); // Array to hold OTP characters
  const [error, setError] = useState('');
  const inputRefs = useRef([]);

  useEffect(() => {
    // Retrieve the stored email from localStorage when the component mounts
    const storedEmail = localStorage.getItem('userEmail');
    if (storedEmail) {
      setEmail(storedEmail);
    }
  }, []);

  const handleVerifyOtp = async () => {
    try {
      const otp = enteredOTP.join(''); // Convert array of OTP characters to a string

      const response = await axios.post('http://192.168.102.131:8282/api/yB/ecommerce/verify-otp', {
        email,
        enteredOTP: otp, // Pass the OTP as a string
      });

      // Handle the verification success as needed
      if (response.status === 200 || response.status === 201) {
        // Navigate to the login page on successful verification
        setError('');

        // Delay navigation to WelcomePage for 6 seconds
        setTimeout(() => {
          navigate('/login');
        }, 6000);
      } else {
        // Handle other status codes
        setError(`Failed to verify OTP. Status Code: ${response.status}`);
      }
    } catch (err) {
      if (err.response && err.response.status === 409) {
        // Handle the conflict
        setError('Invalid OTP.');
      } else {
        // Handle other errors
        setError('Failed to verify OTP. Please try again.');
      }
    }
  };

  // Function to handle changes in the OTP input fields
  const handleOtpChange = (index, value) => {
    const newEnteredOTP = [...enteredOTP];

    // If the value is empty and there's a character at the previous index, move focus to the previous input field
    if (!value && index > 0 && enteredOTP[index - 1]) {
      inputRefs.current[index - 1].focus();
    }

    // Update the entered OTP value
    newEnteredOTP[index] = value;
    setEnteredOTP(newEnteredOTP);

    // Move focus to the next input field if the current input has a value
    if (value && index < enteredOTP.length - 1) {
      inputRefs.current[index + 1].focus();
    }
  };

  return (
    <body className='verify'>



      <div className="value-container">
        <div className="title ">
          Verify OTP
        </div>

        <form action="" className="mt-5">
          {enteredOTP.map((value, index) => (
            <input
              key={index}
              className="otp"
              type="text"
              maxLength="1"
              value={value}
              ref={(ref) => inputRefs.current[index] = ref} // Store input reference in the array
              onChange={(e) => handleOtpChange(index, e.target.value)}
            />
          ))}
        </form>
        <hr className="mt-4" />
        <button onClick={handleVerifyOtp} className='btn btn-primary btn-block mt-4 mb-4 customBtn'>Verify</button>
      </div>




    </body>
  );
};

export default VerifyOtp;
