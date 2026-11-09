package com.litmus7.cataloguemanagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.cataloguemanagement.constants.MessageConstants;
import com.litmus7.cataloguemanagement.constants.StatusCodeMapping;
import com.litmus7.cataloguemanagement.dto.Catalogue;
import com.litmus7.cataloguemanagement.dto.Category;
import com.litmus7.cataloguemanagement.dto.Product;
import com.litmus7.cataloguemanagement.dto.ProductSku;
import com.litmus7.cataloguemanagement.dto.Response;
import com.litmus7.cataloguemanagement.exceptions.CatalogueServiceException;
import com.litmus7.cataloguemanagement.service.CatalogueManagementService;
import com.litmus7.cataloguemanagement.util.StatusPropertiesUtil;
import com.litmus7.cataloguemanagement.util.ValidationUtil;

public class CatalogueManagementController {

	private Logger logger = LogManager.getLogger(CatalogueManagementController.class);
	private CatalogueManagementService catalogueService = new CatalogueManagementService();
	private StatusPropertiesUtil property = new StatusPropertiesUtil();

	/**
	 * The method calls the service layer to fetch the catalog with catalog id
	 * equals to the input parameter.
	 * 
	 * @param catalogueId should be an integer type
	 * @return ResponseDTObject with List<catalogue> type inside the object.
	 */
	public Response<Catalogue> getCatalogueById(int catalogueId) {
		Response<Catalogue> response = null;
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		logger.info("{} catalogue Id: {}", MessageConstants.REQUEST_MESSAGE, catalogueId);
		if (ValidationUtil.isInt(catalogueId)) {
			logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
			try {
				Catalogue catalogue = catalogueService.getCatalogue(catalogueId);
				response = new Response<>(StatusCodeMapping.SUCCESS_CODE, MessageConstants.SUCCESS_MESSAGE, catalogue);
				logger.info("{} : {} with catalogue id {}",property.getProperty(StatusCodeMapping.SUCCESS_CODE) , MessageConstants.DATA_FOUND_MESSAGE,catalogueId);

			} catch (CatalogueServiceException serviceException) {
				logger.error("{} : {}", property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						serviceException.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						MessageConstants.SERVICE_EXCEPTION_MESSAGE + " : " + serviceException.getCause(),
						serviceException.getMessage());
			} catch (Exception e) {
				logger.error("{} : {}", MessageConstants.UNKNOWN_EXCEPTION_MESSAGE, e.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.EXCEPTION_CODE),
						MessageConstants.UNKNOWN_EXCEPTION_MESSAGE + " : " + e.getCause(), e.getMessage());
			}
			logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
			return response;
		} else {
			logger.warn(MessageConstants.ENTER_VALID_INPUT);
			return new Response<>(property.getProperty(StatusCodeMapping.NOT_ACCEPT_CODE),
					MessageConstants.ENTER_VALID_INPUT, "Invalid Input. . .");
		}
	}

	/**
	 * This method calls the service layer to get the root categories of the input
	 * catalogueId from the user
	 * 
	 * @param catalogue catalogueId should be an integer type
	 * @return ResponseDTObject with List<category> type inside the object.
	 */
	public Response<List<Category>> getCatalogueRootCategories(int catalogueId) {
		Response<List<Category>> response = null;
		logger.info("{} catalogue Id: {}", MessageConstants.REQUEST_MESSAGE, catalogueId);
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<Category> category = null;
		if (ValidationUtil.isInt(catalogueId)) {
			try {
				category = catalogueService.getCatalogueRootCategories(catalogueId);
				response = new Response<>(StatusCodeMapping.SUCCESS_CODE, MessageConstants.SUCCESS_MESSAGE, category);
				logger.info("{} : {} with catalogue id {}",
						property.getProperty(StatusCodeMapping.SUCCESS_CODE), MessageConstants.DATA_FOUND_MESSAGE,
						catalogueId);

			} catch (CatalogueServiceException serviceException) {
				logger.error("{} : {}", property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						serviceException.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						MessageConstants.SERVICE_EXCEPTION_MESSAGE + " : " + serviceException.getCause(),
						serviceException.getMessage());
			} catch (Exception e) {
				logger.error("{} : {}", MessageConstants.UNKNOWN_EXCEPTION_MESSAGE, e.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.EXCEPTION_CODE),
						MessageConstants.UNKNOWN_EXCEPTION_MESSAGE + " : " + e.getCause(), e.getMessage());
			}
			logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
			return response;
		} else {
			logger.warn(MessageConstants.ENTER_VALID_INPUT);
			return new Response<>(property.getProperty(StatusCodeMapping.NOT_ACCEPT_CODE),
					MessageConstants.ENTER_VALID_INPUT, null);
		}
	}

	/**
	 * This method calls the service layer to get the immediate sub categories of
	 * the given category in the given catalogue id.
	 * 
	 * @param catalogueId
	 * @param categoryId
	 * @return ResponseDTObject ResponseDTObject with List<category> type inside the
	 *         object.
	 */
	public Response<List<Category>> getCategories(int catalogueId, int categoryId) {
		Response<List<Category>> response = null;
		logger.info("{} catalogue Id: {}, categoryId: {}", MessageConstants.REQUEST_MESSAGE, catalogueId, categoryId);
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		List<Category> category = null;
		if (ValidationUtil.isInt(categoryId) & ValidationUtil.isInt(catalogueId)) {
			try {

				category = catalogueService.getCategories(catalogueId, categoryId);

				response = new Response<>(StatusCodeMapping.SUCCESS_CODE, MessageConstants.SUCCESS_MESSAGE, category);

				logger.info("{} : {} with catalogue id {} and category id {}",
						property.getProperty(StatusCodeMapping.SUCCESS_CODE), MessageConstants.DATA_FOUND_MESSAGE,
						catalogueId, categoryId);
			} catch (CatalogueServiceException serviceException) {
				logger.error("{} : {}", property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						serviceException.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						MessageConstants.SERVICE_EXCEPTION_MESSAGE + " : " + serviceException.getCause(),
						serviceException.getMessage());
			} catch (Exception e) {
				logger.error("{} : {}", MessageConstants.UNKNOWN_EXCEPTION_MESSAGE, e.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.EXCEPTION_CODE),
						MessageConstants.UNKNOWN_EXCEPTION_MESSAGE + " : " + e.getCause(), e.getMessage());
			}
			logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
			return response;
		} else {
			logger.warn(MessageConstants.ENTER_VALID_INPUT);
			return new Response<>(property.getProperty(StatusCodeMapping.NOT_ACCEPT_CODE),
					MessageConstants.ENTER_VALID_INPUT, null);
		}
	}

	/**
	 * This method call the service layer to execute the program to get all products
	 * with category id given by the user.
	 * 
	 * @param catalogueId
	 * @param categoryId
	 * @return ResponseDTObject ResponseDTObject with List<ProductDto> type inside
	 *         the object.
	 */
	public Response<List<Product>> getAllProducts(int catalogueId, int categoryId) {
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		logger.info("{} catalogue Id: {}, categoryId: {}", MessageConstants.REQUEST_MESSAGE, catalogueId, categoryId);
		Response<List<Product>> response = null;
		List<Product> products = null;
		if (ValidationUtil.isInt(categoryId) & ValidationUtil.isInt(catalogueId)) {
			try {
				products = catalogueService.getAllProduct(catalogueId, categoryId);
				response = new Response<>(StatusCodeMapping.SUCCESS_CODE, MessageConstants.SUCCESS_MESSAGE, products);
				logger.info("{} : {} with catalogue id {} and category id {}",
						property.getProperty(StatusCodeMapping.SUCCESS_CODE), MessageConstants.DATA_FOUND_MESSAGE,
						catalogueId, categoryId);
			} catch (CatalogueServiceException serviceException) {
				logger.error("{} : {}", property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						serviceException.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						MessageConstants.SERVICE_EXCEPTION_MESSAGE + " : " + serviceException.getCause(),
						serviceException.getMessage());
			} catch (Exception e) {
				logger.error("{} : {}", MessageConstants.UNKNOWN_EXCEPTION_MESSAGE, e.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.EXCEPTION_CODE),
						MessageConstants.UNKNOWN_EXCEPTION_MESSAGE + " : " + e.getCause(), e.getMessage());
			}
			logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
			return response;
		} else {
			logger.warn(MessageConstants.ENTER_VALID_INPUT);
			return new Response<>(property.getProperty(StatusCodeMapping.NOT_ACCEPT_CODE),
					MessageConstants.ENTER_VALID_INPUT, null);
		}
	}

	/**
	 * This method call the service layer to execute query to find all the skus
	 * available for the given product id.
	 * 
	 * @param productId
	 * @return ResponseDTObject ResponseDTObject with List<ProductSkuDto> type
	 *         inside the object.
	 */
	public Response<List<ProductSku>> getAllSkus(int productId) {
		Response<List<ProductSku>> response = null;
		logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
		logger.info("{} productId: {}", MessageConstants.REQUEST_MESSAGE, productId);
		List<ProductSku> skuDto = null;
		if (ValidationUtil.isInt(productId)) {
			try {
				skuDto = catalogueService.getAllSkus(productId);
				response = new Response<>(StatusCodeMapping.SUCCESS_CODE, MessageConstants.SUCCESS_MESSAGE, skuDto);
				logger.info("{} : {} with id {}", property.getProperty(StatusCodeMapping.SUCCESS_CODE),
						MessageConstants.DATA_FOUND_MESSAGE, productId);
			} catch (CatalogueServiceException serviceException) {
				logger.error("{} : {}", property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						serviceException.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.SERVICE_LAYER_CODE),
						MessageConstants.SERVICE_EXCEPTION_MESSAGE + " : " + serviceException.getCause(),
						serviceException.getMessage());
			} catch (Exception e) {
				logger.error("{} : {}", MessageConstants.UNKNOWN_EXCEPTION_MESSAGE, e.getCause());
				response = new Response<>(property.getProperty(StatusCodeMapping.EXCEPTION_CODE),
						MessageConstants.UNKNOWN_EXCEPTION_MESSAGE + " : " + e.getCause(), e.getMessage());
			}
			logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
			return response;
		} else {
			logger.warn(MessageConstants.ENTER_VALID_INPUT);
			return new Response<>(property.getProperty(StatusCodeMapping.NOT_ACCEPT_CODE),
					MessageConstants.ENTER_VALID_INPUT, null);
		}
	}

//	
}