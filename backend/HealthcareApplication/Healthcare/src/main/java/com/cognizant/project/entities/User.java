package com.cognizant.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userID;
	
	@NotBlank(message="Name is mandatory")
	@Size(min=3,max=30,message="Name should be between 3 and 30 characters")
	@Pattern(regexp="^[A-Za-z ]+$",message="name should contains only alphabets and spaces")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message="Role is mandatory")
	private Role role;
	
	@Email(message="Email should be valid")
	@NotBlank(message="Email is mandatory")
	@Size(max=30,message="Email should not exceed 30 characters")
	@Column(unique=true)
	private String email;
	
	@Pattern(regexp="^[6-9][0-9]{9}$",message="Phone number is invalid")
	@NotBlank(message="Phone number is mandatory")
	@Column(unique=true)
	private String phone;
	
	@NotBlank(message="Password is mandatory")
	@Size(min=5,message="Password must be at least 5 characters long")
	private String password;
	
	public enum Role{
		DOCTOR,PATIENT
	}
}