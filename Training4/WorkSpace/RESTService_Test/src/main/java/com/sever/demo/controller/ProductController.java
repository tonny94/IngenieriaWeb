package com.sever.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sever.demo.model.ProductModel;
import com.sever.demo.repository.ProductRepository;


@RestController
public class ProductController {
	
	private ProductRepository productRepository;
	
	@Autowired
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	/**
	 * Devuelte la lista de todos los productos creados
	 * @return
	 */
	@GetMapping("/list")
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductModel>> listAllProducts() {
		List<ProductModel>  productos = new ArrayList<>();
		productos = productRepository.findAll();
		if (productos.isEmpty()) {
			return new ResponseEntity<List<ProductModel>>(productos,HttpStatus.NO_CONTENT);
        }else {
        	return new ResponseEntity<List<ProductModel>>(productos, HttpStatus.OK);
        }
    }
	
	/**
	 * Inserta un nuevo producto a la base de datos
	 * @param product
	 * @param ucBuilder
	 * @return
	 */
	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(@RequestBody ProductModel product, UriComponentsBuilder ucBuilder) {
       
		if(productRepository.findByCode(product.getCode()) != null) {
            return new ResponseEntity<String>("",HttpStatus.CONFLICT);
        }
    	productRepository.save(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/resume/{code}").buildAndExpand(product.getCode()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	/**
	 * Elimina un producto de la base de datos
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
		ProductModel product = productRepository.deleteByCode(id);
		if (product == null) {
            return new ResponseEntity<ProductModel>(HttpStatus.NOT_FOUND);
        }else {
        	return new ResponseEntity<ProductModel>(HttpStatus.NO_CONTENT);
        }
    }
	
	/**
	 * Muestra un producto
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") String id) {
		ProductModel product = productRepository.findByCode(id);
        if (product == null) {
        	return new ResponseEntity<ProductModel>(HttpStatus.CONFLICT);
        }else {
        	return new ResponseEntity<ProductModel>(product, HttpStatus.OK);
        }
    }
}
