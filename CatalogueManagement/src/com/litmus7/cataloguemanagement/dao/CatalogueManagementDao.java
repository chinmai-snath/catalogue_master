package com.litmus7.cataloguemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.cataloguemanagement.constants.MessageConstants;
import com.litmus7.cataloguemanagement.constants.SqlConstants;
import com.litmus7.cataloguemanagement.constants.StatusCodeMapping;
import com.litmus7.cataloguemanagement.dto.Catalogue;
import com.litmus7.cataloguemanagement.dto.Category;
import com.litmus7.cataloguemanagement.dto.Product;
import com.litmus7.cataloguemanagement.dto.ProductSku;
import com.litmus7.cataloguemanagement.exceptions.CatalogueDaoException;
import com.litmus7.cataloguemanagement.util.ConnectionUtil;

public class CatalogueManagementDao {
	private static final Logger logger = LogManager.getLogger(CatalogueManagementDao.class);

	/**
	 * getCatalogueDao Method returns catalog details of the catalog id given to the
	 * method
	 * 
	 * @param id Inputs catalog id to the method
	 * @return String with details of catalog with catalog id given as input
	 * @throws CatalogueDaoException
	 */
	public Catalogue getCatalogueById(int catalogueId) throws CatalogueDaoException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);

		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.GET_CATALOGUE)) {

			logger.trace(MessageConstants.CONNECTION_MESSAGE);
			preparedStatement.setInt(1, catalogueId);
			Catalogue catalogue = null;
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					catalogue = new Catalogue(resultSet.getInt(SqlConstants.CATALOGUE_ID),
							resultSet.getString(SqlConstants.CATALOGUE_NAME),
							resultSet.getString(SqlConstants.DESCRIPTION));

				}
				logger.info("{} with catalogue id : {}",MessageConstants.DAO_DATA_FETCH_MESSAGE,catalogueId);
				return catalogue;
			}
		} catch (SQLException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueDaoException(MessageConstants.DAO_EXCEPTION_MESSAGE, StatusCodeMapping.SERVER_ERROR_CODE,
					e);
		}

	}

	/**
	 * getCatalogueRootCategoriesDao method returns the data of root categories
	 * 
	 * @return CatalogueManagementDto[] with the details of root category id,
	 *         description and name.
	 * @throws CatalogueDaoException
	 */
	public List<Category> getCatalogueRootCategories(int catalogueId) throws CatalogueDaoException {

		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<Category> categoryDto = new ArrayList<>();
		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SqlConstants.GET_CATALOGUE_ROOT_CATEGORY);) {

			preparedStatement.setInt(1, catalogueId);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				while (resultSet.next()) {
					categoryDto.add(new Category(resultSet.getInt(SqlConstants.CATEGORY_ID),
							resultSet.getString(SqlConstants.DESCRIPTION),
							resultSet.getString(SqlConstants.CATEGORY_NAME)));

				}
				logger.info("{} with catalogue id : {}",MessageConstants.DAO_DATA_FETCH_MESSAGE,catalogueId);
				return categoryDto;
			}
		} catch (SQLException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueDaoException(MessageConstants.DAO_EXCEPTION_MESSAGE, StatusCodeMapping.SERVER_ERROR_CODE,
					e);
		}

	}

	/**
	 * getCategoriesDao method is used to get the Categories from the Database. it
	 * checks for the input category and catalog in the database and returns the
	 * selected rows
	 * 
	 * @param catalogue :Takes in catalog id
	 * @param category  :Takes in category id
	 * @return returns the result with category id and category name
	 * @throws SQLException
	 * @throws CatalogueDaoException
	 * @throws NullPointerException
	 */

	public List<Category> getCategories(int catalogueId, int categoryId) throws CatalogueDaoException {
		List<Category> categoryDto = new ArrayList<>();
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.GET_CATEGORIES);) {

			preparedStatement.setInt(1, catalogueId);
			preparedStatement.setInt(2, categoryId);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				while (resultSet.next()) {
					categoryDto.add(new Category(resultSet.getInt(SqlConstants.CATEGORY_ID),
							resultSet.getString(SqlConstants.CATEGORY_NAME)));

				}
				logger.info("{} with catalogue id : {}, category id: {}",MessageConstants.DAO_DATA_FETCH_MESSAGE,catalogueId,categoryId);
				return categoryDto;
			}
		} catch (SQLException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueDaoException(MessageConstants.DAO_EXCEPTION_MESSAGE, StatusCodeMapping.SERVER_ERROR_CODE,
					e);
		}

	}

	/**
	 * getAllProducts method gets all products from a given category and catalog. it
	 * also returns all the products in the sub categories if the category given is
	 * a root category
	 * 
	 * @param catalogue
	 * @param category
	 * @return returns CatalogueManagementDto object.
	 * @throws CatalogueDaoException
	 */
	public List<Product> getAllProducts(StringJoiner categoryIds, int catalogueId) throws CatalogueDaoException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<Product> products = new ArrayList<>();

		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SqlConstants.GET_ALL_PRODUCTS_FROM_CATEGORY + categoryIds);) {
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				
				while (resultSet.next()) {
					products.add(new Product(resultSet.getInt(SqlConstants.PRODUCT_ID),
							resultSet.getString(SqlConstants.PRODUCT_NAME),
							resultSet.getString(SqlConstants.CATEGORY_NAME),
							resultSet.getInt(SqlConstants.CATEGORY_ID)));

				}
				logger.info("{} with sub category ids: {}",MessageConstants.DAO_DATA_FETCH_MESSAGE,categoryIds);
				return products;
			}
		} catch (SQLException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueDaoException(MessageConstants.DAO_EXCEPTION_MESSAGE, StatusCodeMapping.SERVER_ERROR_CODE,
					e.getCause());
		}
	}

	public List<Integer> getSubCategories(int categoryId, int catalogueId, List<Integer> subCategoriesId)
			throws CatalogueDaoException {

		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);

		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.GET_SUBCATEGORY_ID);) {
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setInt(2, catalogueId);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				while (resultSet.next()) {
					subCategoriesId.add(resultSet.getInt(SqlConstants.CATEGORY_ID));

				}
				logger.info("{} with parent category id: {}",MessageConstants.DAO_DATA_FETCH_MESSAGE,categoryId);
				return subCategoriesId;
			}
		} catch (SQLException e) {

			logger.error(MessageConstants.EXCEPTION_MESSAGE);

			throw new CatalogueDaoException(MessageConstants.DAO_EXCEPTION_MESSAGE, StatusCodeMapping.SERVER_ERROR_CODE,
					e.getCause());
		}
	}

	/**
	 * GET_ALL_SKUS method is used to fetch all the skus of a given product id. it
	 * returns the product name, attributes, values of the product with the product
	 * id matching the input parameter
	 * 
	 * @param product inputs product id as a parameter
	 * @return returns CatalogueManagementDto object. All SKUs of the product with
	 *         product id given to the method is returned as array of objects
	 * @throws CatalogueDaoException
	 */
	public List<ProductSku> getAllSkus(int productId) throws CatalogueDaoException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		Map<Integer, ProductSku> productSkuMap = new HashMap<>();
		ProductSku productSku = null;
		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.GET_ALL_SKUS)) {
			preparedStatement.setInt(1, productId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int ProductSkuId = resultSet.getInt(resultSet.getInt(SqlConstants.SKU_ID));
					ProductSku sku = productSkuMap.get(ProductSkuId);
					if (sku == null) {
						productSku = new ProductSku(ProductSkuId, resultSet.getFloat(SqlConstants.MRP),
								resultSet.getInt(SqlConstants.PRODUCT_ID),
								resultSet.getString(SqlConstants.SKU_DESCRIPTION));
						productSkuMap.put(ProductSkuId, productSku);
					}
					String attributeName = resultSet.getString(SqlConstants.ATTRIBUTE_NAME);
					String attributeValue = resultSet.getString(SqlConstants.ATTRIBUTE_VALUE);
					if (attributeName != null && attributeValue != null) {
						productSku.addAttributes(attributeName, attributeValue);
					}
				}
			}
			logger.info("{} with catalogue id : {}",MessageConstants.DAO_DATA_FETCH_MESSAGE,productId);
		} catch (SQLException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueDaoException(MessageConstants.DAO_EXCEPTION_MESSAGE, StatusCodeMapping.SERVER_ERROR_CODE,
					e);
		}
		List<ProductSku> productSkuList = new ArrayList<>(productSkuMap.values());
		return productSkuList;
	}
}
