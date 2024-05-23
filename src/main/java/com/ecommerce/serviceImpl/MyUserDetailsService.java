
package com.ecommerce.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.Repo.UserRepository;
import com.ecommerce.entity.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}

		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
				new ArrayList<>());
	}
}
