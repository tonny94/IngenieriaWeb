package com.example.practica1.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ActorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idActor;
	private String actor;
	
	public ActorModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	public ActorModel(String actor) {
		this.actor = actor;
	}



	public int getIdActor() {
		return idActor;
	}
	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
}
