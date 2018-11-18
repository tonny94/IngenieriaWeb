package com.sever.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sever.demo.model.ExceptionResponse;
import com.sever.demo.model.ProductException;

@ControllerAdvice
public class ErrorHandlingController {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> generalException(Exception e) throws Exception{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setDescription(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ExceptionResponse> productException(ProductException e) throws ProductException{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.NOT_FOUND);
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		
	}
	

}
