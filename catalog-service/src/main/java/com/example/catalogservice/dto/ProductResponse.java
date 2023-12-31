package com.example.catalogservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private BigDecimal price;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
