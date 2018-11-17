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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sever.demo.controller.ProductController;
import com.sever.demo.model.ProductModel;
import com.sever.demo.repository.ProductRepository;
import org.springframework.web.util.UriComponentsBuilder;

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
	public void listAllProducts_OK() {
		List<ProductModel> listRespuesta = new ArrayList<>();
		listRespuesta.add(product1);
		listRespuesta.add(product2);
		
		ResponseEntity<List<ProductModel>> esperado = new ResponseEntity<List<ProductModel>>(listRespuesta, HttpStatus.OK);
		
		when(productRepository.findAll()).thenReturn(listRespuesta);
		ResponseEntity<List<ProductModel>> resultado = productController.listAllProducts();
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).findAll();
		
	}
	
	@Test
	public void listAllProducts_KO() {
		List<ProductModel> listRespuesta = new ArrayList<>();
		
		ResponseEntity<List<ProductModel>> esperado = new ResponseEntity<List<ProductModel>>(listRespuesta, HttpStatus.NO_CONTENT);
		
		when(productRepository.findAll()).thenReturn(listRespuesta);
		ResponseEntity<List<ProductModel>> resultado = productController.listAllProducts();
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).findAll();
	}
	
	@Test
	public void addProduct_OK() {

		ProductModel nuevoProducto = new ProductModel("C123","platano","1kg de fruta",2);
		UriComponentsBuilder builderEsperado = UriComponentsBuilder.fromUriString("http://localhost:8080");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8080");
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(builderEsperado.path("/resume/{code}").buildAndExpand(nuevoProducto.getCode()).toUri());
		ResponseEntity<?> esperado = new ResponseEntity<String>(header, HttpStatus.CREATED);
		
		when(productRepository.findByCode(nuevoProducto.getCode())).thenReturn(null);
		when(productRepository.save(nuevoProducto)).thenReturn(nuevoProducto);
		
		ResponseEntity<?> resultado = productController.addProduct(nuevoProducto,builder);
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).save(nuevoProducto);
	}
	
	@Test
	public void addProduct_KO() {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8080");
		ResponseEntity<String> esperado = new ResponseEntity<String>("", HttpStatus.CONFLICT);
		
		when(productRepository.findByCode(product1.getCode())).thenReturn(product1);
		
		ResponseEntity<?> resultado = productController.addProduct(product1,builder);
		Assert.assertEquals(esperado, resultado);
		verify(productRepository,times(1)).findByCode(product1.getCode());
	}
	
	@Test
	public void deleteProduct_OK() {

		ResponseEntity<?> esperado = new ResponseEntity<ProductModel>(HttpStatus.NO_CONTENT);
		when(productRepository.deleteByCode(product1.getCode())).thenReturn(product1);
		
		ResponseEntity<?> resultado = productController.deleteProduct("A12");
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).deleteByCode(product1.getCode());
		
	}
	
	@Test
	public void deleteProduct_KO() {
		
		ResponseEntity<ProductModel> esperado = new ResponseEntity<ProductModel>(HttpStatus.NOT_FOUND);
		when(productRepository.deleteByCode("D12")).thenReturn(null);
		
		ResponseEntity<?> resultado = productController.deleteProduct("D12");
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).deleteByCode("D12");
	}
	
	@Test
	public void getProduct_OK() {
		
		ResponseEntity<?> esperado = new ResponseEntity<ProductModel>(product1, HttpStatus.OK);
		when(productRepository.findByCode("A12")).thenReturn(product1);
		
		ResponseEntity<?> resultado = productController.getProduct("A12");
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).findByCode("A12");
	}
	
	@Test
	public void getProduct_KO() {
		
		ProductModel product = null;
		ResponseEntity<?> esperado = new ResponseEntity<ProductModel>(HttpStatus.CONFLICT);
		when(productRepository.findByCode("D12")).thenReturn(product);
		
		ResponseEntity<?> resultado = productController.getProduct("D12");
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).findByCode("D12");
	}
	
	@Test
	public void modifyProduct_OK() {
		
		ProductModel nuevoProducto = new ProductModel("A12","platano","4kg de fruta",4);
		UriComponentsBuilder builderEsperado = UriComponentsBuilder.fromUriString("http://localhost:8080");
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(builderEsperado.path("/resume/{code}").buildAndExpand(nuevoProducto.getCode()).toUri());
		ResponseEntity<?> esperado = new ResponseEntity<String>(header, HttpStatus.CREATED);
		
		when(productRepository.findByCode(product1.getCode())).thenReturn(product1);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8080");
		ResponseEntity<?> resultado = productController.modifyProducts(product1,builder);
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(2)).findByCode(product1.getCode());
		
	}
	
	@Test
	public void modifyProduct_KO() {
		ProductModel nuevoProducto = new ProductModel("D12","platano","4kg de fruta",4);
		ResponseEntity<?> esperado = new ResponseEntity<ProductModel>(HttpStatus.NOT_FOUND);
		
		when(productRepository.findByCode(nuevoProducto.getCode())).thenReturn(null);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8080");
		ResponseEntity<?> resultado = productController.modifyProducts(nuevoProducto,builder);
		Assert.assertEquals(esperado, resultado);
		
		verify(productRepository,times(1)).findByCode(nuevoProducto.getCode());
		
	}
	
	
}
