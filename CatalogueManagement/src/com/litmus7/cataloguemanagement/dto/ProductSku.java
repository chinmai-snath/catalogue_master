package com.litmus7.cataloguemanagement.dto;

import java.util.HashMap;
import java.util.Map;

public class ProductSku {

	private int productId;
	private int skuId;
	private float mrp;
	private String skuDescription;
	public String getSkuDescription() {
		return skuDescription;
	}

	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public void setSkuAttributes(Map<String, String> skuAttributes) {
		this.skuAttributes = skuAttributes;
	}

	private Map<String, String> skuAttributes=new HashMap<>();

	public void addAttributes(String attributeName, String attributeValue) {
		if(attributeName !=null && attributeValue !=null) {
			skuAttributes.put(attributeName, attributeValue);
		}
	}
	
	public Map<String, String> getSkuAttributes(){
		return skuAttributes;
	}

	public ProductSku(int skuId, float mrp, int productId, String skuDescription) {
		// TODO Auto-generated constructor stub
		this.skuId=skuId;
		this.mrp=mrp;
		this.productId = productId;
		this.skuDescription=skuDescription;
	}

	public int getProductId() {
		return productId;
	}

	public int getSkuId() {
		return skuId;
	}

	public float getMrp() {
		return mrp;
	}
}
