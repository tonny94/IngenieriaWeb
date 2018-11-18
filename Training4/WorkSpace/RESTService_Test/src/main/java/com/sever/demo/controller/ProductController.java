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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sever.demo.model.ProductException;
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
    public ResponseEntity<List<ProductModel>> listAllProducts(){
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
	 * @throws ProductException 
	 */
	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(@RequestBody ProductModel product, UriComponentsBuilder ucBuilder) throws ProductException {
       
		if(productRepository.findByCode(product.getCode()) != null) {
            throw new ProductException(1,"El producto ya existe.");
        }else {
        	productRepository.save(product);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/resume/{code}").buildAndExpand(product.getCode()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }
    	
    }
	
	/**
	 * Elimina un producto de la base de datos
	 * @param id
	 * @return
	 * @throws ProductException 
	 */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) throws ProductException {
		
		ProductModel product = productRepository.deleteByCode(id);
		if (product == null) {
			throw new ProductException(2,"El producto no existe.");
        }else {
        	return new ResponseEntity<ProductModel>(HttpStatus.NO_CONTENT);
        }
    }
	
	/**
	 * Muestra un producto
	 * @param id
	 * @return
	 * @throws ProductException 
	 */
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") String id) throws ProductException {
		
		ProductModel product = productRepository.findByCode(id);
        if (product == null) {
        	throw new ProductException(2,"El producto no existe.");
        }else {
        	return new ResponseEntity<ProductModel>(product, HttpStatus.OK);
        }
    }

	/**
	 * Actualiza un producto existente
	 * @param product
	 * @param ucBuilder
	 * @return
	 * @throws ProductException 
	 */
	@PutMapping("/modify")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> modifyProducts(@RequestBody ProductModel product, UriComponentsBuilder ucBuilder) throws ProductException {
		
		if (productRepository.findByCode(product.getCode()) == null) {
			throw new ProductException(2,"El producto no existe.");
		}
        else{
        	productRepository.delete(productRepository.findByCode(product.getCode()));
        	productRepository.save(product);
        	HttpHeaders headers = new HttpHeaders();
    		headers.setLocation(ucBuilder.path("/resume/{code}").buildAndExpand(product.getCode()).toUri());
    		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    	
		}
	}
	
}
