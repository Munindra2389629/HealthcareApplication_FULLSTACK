package com.cognizant.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.project.DTO.UserDTO;
import com.cognizant.project.entities.User;
import com.cognizant.project.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
        this.userService = userService;
    }
	
	@PostMapping("/add")
	public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok(userService.createUser(user));
	}
	
	@GetMapping("/findall")
	public ResponseEntity<List<User>> findUsers() {
		
		return ResponseEntity.ok(userService.findUsers());
	}
	
	@GetMapping("/find/{userID}")
	public ResponseEntity<User> findUser(@PathVariable int userID) {
		return ResponseEntity.ok(userService.findUser(userID));
	}
	
	@PutMapping("/update/{userID}")
	public ResponseEntity<String> updateUser(@PathVariable int userID,@Valid @RequestBody UserDTO userDTO) {
		
		return ResponseEntity.ok(userService.updateUser(userID,userDTO));
	}

}
