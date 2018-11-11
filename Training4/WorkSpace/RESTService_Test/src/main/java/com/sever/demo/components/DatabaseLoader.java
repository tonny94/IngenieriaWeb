package com.sever.demo.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.sever.demo.model.ProductModel;
import com.sever.demo.repository.ProductRepository;

public class DatabaseLoader {
	private ProductRepository productRepository;

    @Autowired
    public DatabaseLoader(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void initDatabase(){

        productRepository.save(
                new ProductModel("CODE001","Lata de coca-cola","Lata de coca-cola de 33cl",51)
        );

        productRepository.save(
                new ProductModel("CODE002","Huevos de campo","Huevos de campo 12 unidades",54)
        );

}
}
