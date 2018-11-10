package com.sever.demo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sever.demo.controller.ProductController;
import com.sever.demo.model.ProductModel;
import com.sever.demo.repository.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RestServiceTestApplicationTests {
	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private ProductController productController;
	
	private ProductModel product1;
	private ProductModel product2;
	
	@org.junit.Before
	public void setUp() {
		product1 = new ProductModel("A12","Coca-cola","bebida",1);
		product2 = new ProductModel("B12","Pepsi","bebida",2);
		
		productController = new ProductController(productRepository);
		
		
		
	}
	
	
	@Test
	public void listProducts_OK() {
		List<ProductModel> listRespuesta = new ArrayList<>();
		listRespuesta.add(product1);
		listRespuesta.add(product2);
		
		ResponseEntity<List<ProductModel>> esperado = new ResponseEntity<List<ProductModel>>(listRespuesta, HttpStatus.OK);
		
		when(productRepository.findAll()).thenReturn(listRespuesta);// .thenReturn(esperado);
		ResponseEntity<List<ProductModel>> resultado = productController.listAllProducts();
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).findAll();
		
	}
	
	@Test
	public void showProduct_OK() {
		
		ResponseEntity<?> esperado = new ResponseEntity<ProductModel>(product1, HttpStatus.OK);
		when(productRepository.findByCode("A12")).thenReturn(product1);
		
		ResponseEntity<?> resultado = productController.getProduct("A12");
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).findByCode("A12");
		
	}
	
	
}
