package com.example.catalogservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogservice.dto.ProductAdapter;
import com.example.catalogservice.dto.ProductResponse;
import com.example.catalogservice.entity.Product;
import com.example.catalogservice.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
    @Autowired 
    private ProductRepository productRepository;
    
    @Autowired 
    private ProductAdapter productAdapter;
    
    @GetMapping()
    public List<ProductResponse> list(){
    	final List<Product> all = productRepository.findAll();
    	return productAdapter.of(all);
    }
    
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
    	log.info("--- product por id  " + id);
    	final Product product = productRepository.findById(id).orElseThrow();
    	return productAdapter.of(product);
    }
    
    @PostMapping()
    public ProductResponse addProduct(@RequestBody Product product) {
    	final Product p = productRepository.save(product);
    	return productAdapter.of(p);
    }

}
