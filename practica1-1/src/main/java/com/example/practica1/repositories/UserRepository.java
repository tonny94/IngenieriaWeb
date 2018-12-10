package com.example.practica1.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.practica1.models.UserModel;

public interface UserRepository extends CrudRepository<UserModel,String>{
	
	UserModel findByName(String name);
	List<UserModel> findAll();
	UserModel deleteById(int id);
	UserModel save(UserModel user);
	
}
