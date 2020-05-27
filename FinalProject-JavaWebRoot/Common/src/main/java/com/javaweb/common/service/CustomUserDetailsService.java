package com.javaweb.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.javaweb.common.entity.User;
import com.javaweb.common.repository.UserRepository;

/**
 * @author DINH SY HUNG
 * @version 1.0
 * @since 2020-01-05
 */

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("Username: " + email + " not found"));
		 return user;
	}

	
	
}
