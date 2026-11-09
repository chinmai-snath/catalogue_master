package com.litmus7.cataloguemanagement.dto;

public class Catalogue {

	private int catalogueId;
	private String catalogueName;
	private String description;

	public Catalogue(int catalogueId, String catalogueName, String description) {
		this.catalogueId = catalogueId;
		this.catalogueName = catalogueName;
		this.description = description;
	}

	public int getCatalogueId() {
		return catalogueId;
	}

	public String getCatalogueName() {
		return catalogueName;
	}

	public String getDescription() {
		return description;
	}
}
