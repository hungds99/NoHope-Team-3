package com.javaweb.dataseed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.javaweb.common.entity.User;
import com.javaweb.common.repository.UserRepository;

/**
 * @author DINH SY HUNG
 * @version 1.0
 * @since 2020-01-05
 */

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if (userRepository.findAll().isEmpty()) {
			User user = new User();
			user.setUsername("Dinh Sy Hung");
			user.setEmail("admin@gmail.com");
			user.setPassword(passwordEncoder.encode("1234"));
			userRepository.save(user);
		}
		
	}

}
