package com.example.practica1.models;

import java.util.Map;

public class ResponseModel {

	private Map<String,?> responseObject;

	public Map<String, ?> getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Map<String, ?> responseObject) {
		this.responseObject = responseObject;
	}

	public ResponseModel(Map<String, ?> responseObject) {
		super();
		this.responseObject = responseObject;
	}
	
	
	public ResponseModel() {
		super();
	}
	
}
