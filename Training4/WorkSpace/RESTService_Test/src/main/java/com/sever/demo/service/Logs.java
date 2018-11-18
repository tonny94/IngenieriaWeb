package com.sever.demo.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sever.demo.controller.ProductController;
import com.sever.demo.model.ProductModel;

import ch.qos.logback.classic.Logger;

@Service
public class Logs {
    @Autowired
	private Logger historial = (Logger) LoggerFactory.getLogger(ProductController.class);

    public void writeConsole(ProductModel value) {

    	historial.trace("Product with code" + value.getCode() + "is trace");

    	historial.debug("Product with code" + value.getCode() + "is sent");

    	historial.info("Product Added");

    	historial.warn("There is no product");

    	historial.error("Error in database");

    }
}
