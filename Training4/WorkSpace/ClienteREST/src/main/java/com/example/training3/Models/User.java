package com.example.training3.Models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User implements UserDetailsService {
	@Id
	private String nombre;
	private String password;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<GrantedAuthority> roles;
	
	public User () {}
	
	public User(String name, String pass, List<GrantedAuthority> roles) {
		this.nombre = name;
		this.password = new BCryptPasswordEncoder().encode(pass);
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public List<GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		return null;
	}
}
