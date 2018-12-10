package com.example.practica1.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserLoginModel {
	@Id
	private String name;
	private String password;
	
	public UserLoginModel() {
		super();
	}
	
	public UserLoginModel(String name, String password) {
		this.name = name;
		this.password = password;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
