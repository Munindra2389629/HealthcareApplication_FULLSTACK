package com.cognizant.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.project.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);
}
