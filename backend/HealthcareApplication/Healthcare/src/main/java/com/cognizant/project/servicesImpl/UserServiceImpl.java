package com.cognizant.project.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.project.DTO.UserDTO;
import com.cognizant.project.entities.User;
import com.cognizant.project.exceptions.ResourceNotFoundException;
import com.cognizant.project.repositories.UserRepository;
import com.cognizant.project.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public String createUser(User user) {
		
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User created successfully with Id: "+user.getUserID();
	}

	public List<User> findUsers() {
		
		return userRepository.findAll();
	}

	public User findUser(int userID) {
		
		User user=userRepository.findById(userID).orElseThrow(() ->
					new ResourceNotFoundException("user","ID",""+userID));	
		
		return user;
	}
	
	public String updateUser(int userID,UserDTO userDTO) {
		
		User user=userRepository.findById(userID).orElseThrow(() ->
					new ResourceNotFoundException("user","ID",""+userID));
		
		if(userDTO.getName()!=null) {
			user.setName(userDTO.getName());
		}
		if(userDTO.getPhone()!=null) {
			user.setPhone(userDTO.getPhone());
		}
		userRepository.save(user);
		return "User Updated successfully";
	}	
}
