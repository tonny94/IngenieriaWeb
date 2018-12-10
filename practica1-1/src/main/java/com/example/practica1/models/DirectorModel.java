package com.example.practica1.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class DirectorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idDirector;
	private String director;
	
	
	public DirectorModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DirectorModel( String director) {
		this.director = director;
	}
	public int getIdDirector() {
		return idDirector;
	}
	public void setIdDirector(int idDirector) {
		this.idDirector = idDirector;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	
}
