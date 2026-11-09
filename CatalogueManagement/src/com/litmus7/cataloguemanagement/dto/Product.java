package com.litmus7.cataloguemanagement.dto;

public class Product {
	
	private int categoryId;
	private String categoryName;
	private String productName;
	private int productId;

	public Product(int productId, String productName, String categoryName, int categoryId) {
		this.productId = productId;
		this.productName = productName;
		this.categoryName = categoryName;
		this.categoryId = categoryId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getProductName() {
		return productName;
	}

	public int getProductId() {
		return productId;
	}

	
}
