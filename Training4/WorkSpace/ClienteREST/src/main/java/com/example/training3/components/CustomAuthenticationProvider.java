package com.example.training3.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.training3.models.User;
import com.example.training3.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) {
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user;
		try {
			user = userRepository.findByNombre(username);
		} catch(Exception e) {
			throw new BadCredentialsException("User not found");
		}
		
		if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}

		return new UsernamePasswordAuthenticationToken(username, password, user.getRoles());

	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}
