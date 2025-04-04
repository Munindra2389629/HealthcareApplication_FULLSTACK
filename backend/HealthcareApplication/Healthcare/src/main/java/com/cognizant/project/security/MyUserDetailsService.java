package com.cognizant.project.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.project.entities.User;
import com.cognizant.project.exceptions.ResourceNotFoundException;
import com.cognizant.project.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new ResourceNotFoundException("User","ID",email);
		}
		
		String role="ROLE_"+user.getRole();
		List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority(role));
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(),authorities);
	}

}
