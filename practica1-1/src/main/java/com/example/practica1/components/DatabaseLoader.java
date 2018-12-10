package com.example.practica1.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.practica1.models.UserModel;
import com.example.practica1.repositories.UserRepository;

@Component
public class DatabaseLoader {
	private UserRepository userRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initDatabase(){

    	//userRepository.deleteAll();
    	if(userRepository.findByName("sa") == null) {
    		userRepository.save(
                    new UserModel("sa","admin",0)
            );
    	};
    	
    	if(userRepository.findByName("usuario") == null) {
	    	userRepository.save(
	                new UserModel("usuario","12345",1)
	        );
	    };

    }
}
