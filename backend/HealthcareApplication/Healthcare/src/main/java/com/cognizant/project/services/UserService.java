package com.cognizant.project.services;

import java.util.List;

import com.cognizant.project.DTO.UserDTO;
import com.cognizant.project.entities.User;

public interface UserService {

	String createUser(User user);

	List<User> findUsers();

	User findUser(int userID);

	String updateUser(int userID, UserDTO userDTO);
}
