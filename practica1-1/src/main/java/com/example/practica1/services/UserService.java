package com.example.practica1.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.practica1.models.UserModel;
import com.example.practica1.repositories.UserRepository;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<UserModel> findAll() {
		return (List<UserModel>) userRepository.findAll();
	};

	public UserModel save(UserModel user) {
		try {
			UserModel newUser = userRepository.save(user);
			return newUser;
		} catch (Exception e2) {
			return null;
		}
	};

	public UserModel deleteById(int id) {
		try {
			UserModel deletedUser = userRepository.deleteById(id);
			return deletedUser;
		} catch (Exception e2) {
			return null;
		}
	};

	public UserModel findByName(String name) {
		try {
			UserModel user = userRepository.findByName(name);
			return user;
		} catch (Exception e2) {
			return null;
		}
	}

}
