package com.sever.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		List<ProductModel>  productos = productRepository.findAll();
		if (productos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
        }else {
        	//.stream().collect(Collectors.toList())
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
    public ResponseEntity<?> addProducts(@RequestBody ProductModel product, UriComponentsBuilder ucBuilder) {
       
		if(productRepository.findByCode(product.getCode()) != null) {
			//new Error("El producto " + product.getName() + " ya existe.","","",""),
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    	productRepository.save(new ProductModel(product.getCode(),product.getName(),product.getDescription(),product.getPrice()));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/resume/{code}").buildAndExpand(product.getCode()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	/**
	 * Elimina un producto de la base de datos
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
		ProductModel product = productRepository.findByCode(id);
        if (product == null) {
        	//new Error("No se puede borrar el producto "+id,"","",""),
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productRepository.delete(productRepository.findByCode(id));
        return new ResponseEntity<ProductModel>(HttpStatus.NO_CONTENT);
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
        	//new Error("El producto " + id + " no existe.","","",""),
        	return new ResponseEntity(HttpStatus.CONFLICT);
            }
        return new ResponseEntity<ProductModel>(product, HttpStatus.OK);
    }
}
