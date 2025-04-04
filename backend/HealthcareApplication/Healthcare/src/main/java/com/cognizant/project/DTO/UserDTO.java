package com.cognizant.project.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
	
	@Size(min=3,max=30,message="Name should be between 3 and 30 characters")
	@Pattern(regexp="^[A-Za-z ]+$")
	private String name;
	
	@Pattern(regexp="^[6-9][0-9]{9}$",message="Phone number is invalid")
	@Column(unique=true)
	private String phone;
}
