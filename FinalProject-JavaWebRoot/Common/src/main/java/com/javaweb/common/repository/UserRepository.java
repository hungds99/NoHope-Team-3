package com.javaweb.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.common.entity.User;

/**
 * @author DINH SY HUNG
 * @version 1.0
 * @since 2020-01-05
 */

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	
}
