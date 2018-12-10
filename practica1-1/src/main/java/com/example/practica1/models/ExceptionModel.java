package com.example.practica1.models;

import org.springframework.stereotype.Component;

@Component
public class ExceptionModel extends Exception{

	private static final long serialVersionUID = 1L;
	
	private int code;
	private String description;
	
	
	
	public ExceptionModel(int code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	
	public ExceptionModel() {
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
