package com.example.training3.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.training3.models.User;

public interface UserRepository extends CrudRepository<User, String>{
	List<User> findAll();
	User findByNombre(String nombre);
}
