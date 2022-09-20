package com.ecommerce.app.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class ProductDto {

	private Integer id;
	
	@NotNull
	private String name;
	
	@Column(name = "image_url")
	@NotNull
	private String imageUrl;
	
	@NotNull
	private double price;
	
	@NotNull
	private String description;
	
	@NotNull
	private Integer categoryId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
}
