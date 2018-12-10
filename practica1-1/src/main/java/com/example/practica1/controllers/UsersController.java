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
import com.example.practica1.models.UserModel;
import com.example.practica1.services.UserService;

@RestController
public class UsersController {

private static final String NOT_EXITS = "El usuario no existe.";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Devuelte la lista de todos los usuarios creados
	 * @return
	 */
	@GetMapping("/listUsers")
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserModel>> listAllProducts(){
		List<UserModel>  users = new ArrayList<>();
		users = userService.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<UserModel>>(users,HttpStatus.NO_CONTENT);
		}else {
        	return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
        }
    }
	
	/**
	 * Inserta un nuevo usuario a la base de datos
	 * @param user
	 * @param ucBuilder
	 * @return
	 * @throws UserException 
	 */
	@PostMapping("/addUser")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addUser(@RequestBody UserModel user, UriComponentsBuilder ucBuilder) throws UserException {
       
		if(userService.findByName(user.getName()) != null) {
            throw new UserException(1,"El usuario ya existe.");
        }else {
        	userService.save(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/resumeUser/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }
    	
    }
	
	/**
	 * Elimina un usuario de la base de datos
	 * @param id
	 * @return
	 * @throws UserException 
	 */
	@GetMapping("/removeUser/{id}")
    public ResponseEntity<UserModel> deleteProduct(@PathVariable("id") String id) throws UserException {
		
		int userId = Integer.parseInt(id);
		UserModel product = userService.deleteById(userId);
		if (product == null) {
			throw new UserException(2,NOT_EXITS);
        }else {
        	return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
        }
    }
	
	/**
	 * Muestra un usuario
	 * @param id
	 * @return
	 * @throws UserException 
	 */
	@GetMapping("/showUser/{id}")
    public ResponseEntity<UserModel> getProduct(@PathVariable("id") String id) throws UserException {
		
		UserModel user = userService.findByName(id);
        if (user == null) {
        	throw new UserException(2,NOT_EXITS);
        }else {
        	return new ResponseEntity<UserModel>(user, HttpStatus.OK);
        }
    }

	/**
	 * Actualiza un producto existente
	 * @param product
	 * @param ucBuilder
	 * @return
	 * @throws UserException 
	 */
	@PutMapping("/modifyUser")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> modifyProducts(@RequestBody UserModel user, UriComponentsBuilder ucBuilder) throws UserException {
		
		if (userService.findByName(user.getName()) == null) {
			throw new UserException(2,NOT_EXITS);
		}
        else{

    		int userID = user.getId();
        	userService.deleteById(userID);
        	userService.save(user);
        	HttpHeaders headers = new HttpHeaders();
    		headers.setLocation(ucBuilder.path("/resumeUser/{id}").buildAndExpand(userID).toUri());
    		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    	
		}
	}
}
