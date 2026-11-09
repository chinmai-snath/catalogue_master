package com.litmus7.cataloguemanagement.constants;

public class SqlConstants {

	public static String GET_ALL_SKUS="SELECT p.product_id, p.product_name, s.sku_id, s.sku_description, s.mrp ,a.Attribute_name,a.attribute_value FROM SKU_DEtails s  "
				+ "INNER Join  Product_details p  ON p.product_id=s.product_id "
				+ "inner JOIN sku_attributes a ON s.SKU_ID=A.SKU_ID "
				+ "WHERE s.product_id=?  ORDER BY s.SKU_ID";
	
	
	public static String GET_CATALOGUE="select catelogue_id, catelogue_name ,description from catalogues WHERE catelogue_id=?";
	
	public static String GET_CATALOGUE_ROOT_CATEGORY= "Select root.category_id,c.description, cat.category_name from root_categories root INNER JOIN catalogues c ON root.catelogue_id=c.catelogue_id INNER JOIN category_details cat ON root.category_id=cat.category_id WHERE c.catelogue_id=?";
	
	
	public static String GET_SUBCATEGORY_ID= "select category_id from parent_categories where parent_category_id=? AND catelogue_id=?";
	
	public static String GET_CATEGORIES= "Select r.category_id , c.category_name from parent_categories r, category_details c where catelogue_id=? AND parent_category_id=? AND c.category_id=r.category_id";
	
	
	public static String GET_ALL_PRODUCTS_FROM_CATEGORY= "SELECT  c.product_id, p.product_name, cat.category_name, c.category_id FROM Product_categories c INNER JOIN product_details p on p.product_id=c.product_id "
				+ "Inner Join category_details cat ON c.category_id=cat.category_id "
				+ "Where c.category_id In  ";
	
	
	public final static String SELECT_PRODUCTS = "SELECT product_details.product_id,product_name,base_price FROM product_details,product_categories\r\n"
			+ "WHERE product_details.product_id=product_categories.product_id\r\n"
			+ "AND catalogue_id=? AND category_id=?;";
	
	public final static String CATALOGUE_ID="catelogue_id";
	public final static String CATALOGUE_NAME="catelogue_name";
	public final static String DESCRIPTION="description";
	public final static String CATEGORY_ID="category_id";
	public final static String CATEGORY_NAME="category_name";
	public final static String PRODUCT_ID="product_id";
	public final static String PRODUCT_NAME="product_name";
	
	 public final static String SKU_ID="sku_id";
	 public final static String SKU_DESCRIPTION="sku_description";
	 public final static String MRP="mrp";
	 public final static String ATTRIBUTE_NAME="Attribute_name";
	 public final static String ATTRIBUTE_VALUE="attribute_value";
	 
	 public static final String DB_CONFIG_FILE="dbmsconfig.properties";
	 public static final String DB_URL="dburl";
	 public static final String DB_PASSWORD="password";
	 public static final String DB_USER="user";
}