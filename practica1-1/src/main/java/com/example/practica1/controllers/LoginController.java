package com.example.practica1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.practica1.models.UserException;
import com.example.practica1.models.UserLoginModel;
import com.example.practica1.models.UserModel;
import com.example.practica1.services.UserService;

@RestController
public class LoginController {

private static final String NOT_EXITS = "El usuario no existe.";
private static final String ERROR_DATA = "Error con las credenciales.";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}
	

	/**
	 * Consulta si el usuario existe
	 * @param userLogin
	 * @param ucBuilder
	 * @return
	 * @throws UserException 
	 */
	@GetMapping("/existUser")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> modifyProducts(@RequestBody UserLoginModel userLogin, UriComponentsBuilder ucBuilder) throws UserException {
		
		UserModel user = userService.findByName(userLogin.getName());
		if (user == null) {
			throw new UserException(2,NOT_EXITS);
		}
        else if (user.getName() != userLogin.getName() || user.getPassword() != userLogin.getPassword()){
        	throw new UserException(2,ERROR_DATA);
        } 
        else{
    		HttpHeaders headers = new HttpHeaders();
    		headers.setLocation(ucBuilder.path("/index").build().toUri());
    		return new ResponseEntity<String>(headers, HttpStatus.OK);
		}
	}
	
	
}
