package com.litmus7.cataloguemanagement.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.cataloguemanagement.constants.MessageConstants;
import com.litmus7.cataloguemanagement.dao.CatalogueManagementDao;
import com.litmus7.cataloguemanagement.dto.Catalogue;
import com.litmus7.cataloguemanagement.dto.Category;
import com.litmus7.cataloguemanagement.dto.Product;
import com.litmus7.cataloguemanagement.dto.ProductSku;
import com.litmus7.cataloguemanagement.exceptions.CatalogueDaoException;
import com.litmus7.cataloguemanagement.exceptions.CatalogueServiceException;
import com.litmus7.cataloguemanagement.exceptions.DataNotFoundException;

public class CatalogueManagementService {

	private Logger logger = LogManager.getLogger(CatalogueManagementService.class);
	private CatalogueManagementDao catalogueDao = new CatalogueManagementDao();

	/**
	 * Returns data with catalogue Id given by the user. Data is taken from the dao
	 * and returned to the controller
	 * 
	 * @param catalogueId
	 * @return
	 * @throws CatalogueServiceException
	 * @throws DataNotFoundException 
	 */
	public Catalogue getCatalogue(int catalogueId) throws CatalogueServiceException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		Catalogue catalogue = null;
		try {
			logger.info("{} catalogueId: {}",MessageConstants.REQUEST_MESSAGE_DAO,catalogueId);
			catalogue = catalogueDao.getCatalogueById(catalogueId);
			logger.debug("{} rows fetched");
			if (catalogue == null) {
				logger.debug("No rows fetched");
				logger.info("{} with catalogue id : {}",MessageConstants.DATA_NOT_FETCH_MESSAGE,catalogueId);
				throw new DataNotFoundException(MessageConstants.NO_DATA_FOUND_MESSAGE);
			}
			logger.info("{} with catalogue id : {}",MessageConstants.DATA_FETCH_MESSAGE,catalogueId);
		} catch (CatalogueDaoException | DataNotFoundException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueServiceException(MessageConstants.SERVICE_EXCEPTION_MESSAGE, e);
		}
		logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
		return catalogue;
	}

	/**
	 * Returns root categories of the given catalogue. It gets the data from the dao
	 * and return it to the controller.
	 * 
	 * @param catalogueId
	 * @return
	 * @throws CatalogueServiceException
	 * @throws DataNotFoundException 
	 */
	public List<Category> getCatalogueRootCategories(int catalogueId) throws CatalogueServiceException, DataNotFoundException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<Category> categories = null;
		try {
			logger.info("{} catalogueId: {}",MessageConstants.REQUEST_MESSAGE_DAO,catalogueId);
			categories = catalogueDao.getCatalogueRootCategories(catalogueId);
			logger.info("{} with catalogue id : {}",MessageConstants.DATA_FETCH_MESSAGE,catalogueId);
			if (categories.size() > 0) {
				logger.debug("{} rows fetched", categories.size());
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				logger.info("{} with catalogue id : {}",MessageConstants.DATA_FETCH_MESSAGE,catalogueId);
				return categories;
			} else {
				logger.debug("{} rows fetched", categories.size());
				logger.info("{} with catalogue id : {}",MessageConstants.DATA_NOT_FETCH_MESSAGE,catalogueId);
				throw new DataNotFoundException(MessageConstants.NO_DATA_FOUND_MESSAGE);
			}
		} catch (CatalogueDaoException | DataNotFoundException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueServiceException(MessageConstants.SERVICE_EXCEPTION_MESSAGE, e);
		}

	}

	/**
	 * Get all immediate sub categories of a given category.
	 * 
	 * @param catalogueId
	 * @param categoryId
	 * @return
	 * @throws CatalogueServiceException
	 * @throws DataNotFoundException 
	 * @throws SQLException
	 * @throws CatalogueDaoException
	 */
	public List<Category> getCategories(int catalogueId, int categoryId) throws CatalogueServiceException, DataNotFoundException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<Category> categories = null;
		try {
			logger.info("{} catalogueId: {}, categoryId: {}",MessageConstants.REQUEST_MESSAGE_DAO,catalogueId, categoryId);
			categories = catalogueDao.getCategories(catalogueId, categoryId);

			if (categories.size() > 0) {
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				logger.debug("{} rows fetched", categories.size());
				logger.info("{} with catalogue id : {}, category Id: {}",MessageConstants.DATA_FETCH_MESSAGE,catalogueId,categoryId);
				return categories;
			} else {
				logger.debug("{} rows fetched", categories.size());
				logger.info("{} with catalogue id : {}, and category id: {}",MessageConstants.DATA_NOT_FETCH_MESSAGE,catalogueId, categoryId);
				throw new DataNotFoundException(MessageConstants.NO_DATA_FOUND_MESSAGE);
			}

		} catch (CatalogueDaoException | DataNotFoundException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueServiceException(MessageConstants.SERVICE_EXCEPTION_MESSAGE, e);
		}

	}

	/**
	 * @param catalogueId
	 * @param categoryId
	 * @return
	 * @throws CatalogueServiceException
	 * @throws DataNotFoundException 
	 */
	public List<Product> getAllProduct(int catalogueId, int categoryId) throws CatalogueServiceException, DataNotFoundException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<Product> product = null;
		try {
			logger.info("{} catalogueId: {}, categoryId: {}",MessageConstants.REQUEST_MESSAGE_DAO,catalogueId, categoryId);
			List<Integer> subCategoriesQueue = new ArrayList<>();
			List<Integer> subCategoryIdList = new ArrayList<>();
			subCategoriesQueue.add(categoryId);
			while(subCategoriesQueue.size()!=0) {
				int subCategory = subCategoriesQueue.get(0);
				subCategoryIdList.add(subCategory);
				catalogueDao.getSubCategories(subCategory, catalogueId, subCategoriesQueue);
				subCategoriesQueue.remove(0);
			}
			StringJoiner subIds=new StringJoiner(",","(",")");
			for (int j : subCategoryIdList) {
				subIds.add(String.valueOf(j));
			}
			product = catalogueDao.getAllProducts(subIds,  catalogueId);
			if (product.size()>0) {
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				logger.debug("{} rows fetched", product.size());
				logger.info("{} with catalogue id : {}, category Id: {}",MessageConstants.DATA_FETCH_MESSAGE,catalogueId,categoryId);
				return product;
			} else {
				logger.debug("{} rows fetched", product.size());
				logger.info("{} with catalogue id : {}, and category id: {}",MessageConstants.DATA_NOT_FETCH_MESSAGE,catalogueId, categoryId);
				throw new DataNotFoundException(MessageConstants.NO_DATA_FOUND_MESSAGE);
			}
		} catch (CatalogueDaoException | DataNotFoundException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueServiceException(MessageConstants.SERVICE_EXCEPTION_MESSAGE, e);
		}
		
	}

	/**
	 * Gets all SKU Attributes
	 * 
	 * @param productId
	 * @return
	 * @throws CatalogueServiceException
	 * @throws DataNotFoundException 
	 */
	public List<ProductSku> getAllSkus(int productId) throws CatalogueServiceException, DataNotFoundException {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<ProductSku> skus = null;
		try {
			logger.info("{} productId: {}",MessageConstants.REQUEST_MESSAGE_DAO,productId);
			skus = catalogueDao.getAllSkus(productId);

			if (skus.size() > 0) {
				logger.debug("{} rows fetched", skus.size());
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				logger.info("{} with product id : {}",MessageConstants.DATA_FETCH_MESSAGE,productId);
				return skus;
			} else {
				logger.debug("{} rows fetched", skus.size());
				logger.info("{} with product id : {}",MessageConstants.DATA_NOT_FETCH_MESSAGE,productId);
				throw new DataNotFoundException(MessageConstants.NO_DATA_FOUND_MESSAGE);
			}

		} catch (CatalogueDaoException | DataNotFoundException e) {
			logger.error(MessageConstants.EXCEPTION_MESSAGE);
			throw new CatalogueServiceException(MessageConstants.SERVICE_EXCEPTION_MESSAGE, e);
		}

	}

}
