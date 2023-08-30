package com.example.catalogservice.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.catalogservice.entity.Product;

/*
 * La utilizo para convertir de Entity a DTO
 */

@Component
public class ProductAdapter {

	public ProductResponse of(Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(product.getId());
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		return productResponse;
	}

	public List<ProductResponse> of(List<Product> products) {
		return products.stream()
				.map(p -> of(p))
				.collect(Collectors.toList());
	}

}
