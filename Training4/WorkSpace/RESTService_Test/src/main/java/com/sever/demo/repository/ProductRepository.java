package com.sever.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sever.demo.model.ProductModel;

public interface ProductRepository extends CrudRepository<ProductModel,String>{
	List<ProductModel> findAll();
	ProductModel findByCode(String code);
}
