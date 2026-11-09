package com.litmus7.cataloguemanagement.dto;

public class Category {

	private int categoryId;
	private String categoryName;
	private String description;

	public Category(int categoryId, String categoryName, String description) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.description = description;
	}

	public Category(int categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	public int getcategoryId() {
		return categoryId;
	}

	public String getcategoryName() {
		return categoryName;
	}

	public String getDescription() {
		return description;
	}
}