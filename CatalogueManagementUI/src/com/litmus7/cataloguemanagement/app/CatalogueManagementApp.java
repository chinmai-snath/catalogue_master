package com.litmus7.cataloguemanagement.app;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.cataloguemanagement.constants.MessageConstants;
import com.litmus7.cataloguemanagement.constants.StatusCodeMapping;
import com.litmus7.cataloguemanagement.controller.CatalogueManagementController;
import com.litmus7.cataloguemanagement.dto.Catalogue;
import com.litmus7.cataloguemanagement.dto.Category;
import com.litmus7.cataloguemanagement.dto.Product;
import com.litmus7.cataloguemanagement.dto.ProductSku;
import com.litmus7.cataloguemanagement.dto.Response;

public class CatalogueManagementApp {

	private static final Logger logger = LogManager.getLogger(CatalogueManagementApp.class);

	public static void main(String[] args) {

		CatalogueManagementController catalogueController = new CatalogueManagementController();	
		int choice;
		Scanner scan = new Scanner(System.in);
		int catalogueId;
		int categoryId;
		int productId;
		Response<Catalogue> responseCatalogue=null;
		Response<List<Category>> responseDtoCategory=null;
		Response<List<Product>> responseDtoProduct=null;
		Response<List<ProductSku>> responseDtoSkus=null;
		do {
			logger.trace("Starting the application . . . ");
			System.out.println(" 1.getCatalogById() \n 2.getCatalogRootCategories \n 3.getCategories "
					+ "\n 4.getAllProducts \n 5.getAllSkus \n 6. Exit");
			choice = scan.nextInt();

			switch (choice) {
			case 1:
				logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
				System.out.println(MessageConstants.GET_CATALOGUE_MESSAGE);
				int id = scan.nextInt();
				responseCatalogue = catalogueController.getCatalogueById(id);

				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				
				if (responseCatalogue.getStatusCode() == StatusCodeMapping.SUCCESS_CODE) {
					System.out.println("Fetched result: . . . \n" );
					System.out.println("Catalogue id: "+responseCatalogue.getData().getCatalogueId()+"\nCatalogue Name: "+responseCatalogue.getData().getCatalogueName()
							+"\nDescription: "+responseCatalogue.getData().getDescription()+"\n");
					logger.info("{} with id {}",MessageConstants.DATA_FOUND_MESSAGE,id);
				} else {
					System.out.println(MessageConstants.NO_DATA_FOUND_MESSAGE);
					System.out.println("Exceptions occured: " + responseCatalogue.getStatusCode());
					logger.error(MessageConstants.NO_DATA_FOUND_MESSAGE);
				}

				break;

			case 2:
				System.out.println(MessageConstants.GET_CATALOGUE_MESSAGE);
				catalogueId = scan.nextInt();
				logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
				responseDtoCategory = catalogueController.getCatalogueRootCategories(catalogueId);
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				if (responseDtoCategory.getStatusCode() == StatusCodeMapping.SUCCESS_CODE) {

				for(Category categoryDto: responseDtoCategory.getData()) {
					
						System.out.println("Category id: " + categoryDto.getcategoryId() + "\nDescription: "
								+ categoryDto.getDescription() + "\nCategory Name: " + categoryDto.getcategoryName() + "\n");
					}
				logger.info(MessageConstants.DATA_FOUND_MESSAGE);
				} else {

					System.out.println(MessageConstants.NO_DATA_FOUND_MESSAGE);
					System.out.println("Exceptions occured: " + responseDtoCategory.getStatusCode());
					logger.error(MessageConstants.NO_DATA_FOUND_MESSAGE);
				}

				break;

			case 3:
				System.out.println(MessageConstants.GET_CATALOGUE_MESSAGE);
				catalogueId = scan.nextInt();
				System.out.println(MessageConstants.GET_CATEGORY_MESSAGE);
				categoryId = scan.nextInt();
				logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
				responseDtoCategory=catalogueController.getCategories(catalogueId, categoryId);
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				if (responseDtoCategory.getStatusCode() == StatusCodeMapping.SUCCESS_CODE) {
					System.out.println("SubCategories of :"+categoryId+" In catalogue: "+catalogueId+"\n");
					for(Category categoryDto: responseDtoCategory.getData()) {
						System.out.println("Category id: " + categoryDto.getcategoryId() + "\nCategory Name: " + categoryDto.getcategoryName() + "\n");
					}
					logger.info(MessageConstants.DATA_FOUND_MESSAGE);
				} else {

					System.out.println(MessageConstants.NO_DATA_FOUND_MESSAGE);
					System.out.println("Exceptions occured: " + responseDtoCategory.getMessage());
					logger.error(MessageConstants.NO_DATA_FOUND_MESSAGE);
				}
				break;

			case 4:
				System.out.println(MessageConstants.GET_CATALOGUE_MESSAGE);
				catalogueId = scan.nextInt();
				System.out.println(MessageConstants.GET_CATEGORY_MESSAGE);
				categoryId = scan.nextInt();
				logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
				responseDtoProduct = catalogueController.getAllProducts(catalogueId, categoryId);
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				if (responseDtoProduct.getStatusCode() == StatusCodeMapping.SUCCESS_CODE) {
					System.out.println("SubCategories of :"+categoryId+" In catalogue: "+catalogueId+"\n");
					for(Product productDto: responseDtoProduct.getData()) {
						System.out.println("Product id: " + productDto.getProductId() + "\nProduct Name: " + productDto.getProductName() +"\nCategory id: " + productDto.getCategoryId() + "\nCategory Name: " + productDto.getCategoryName() + "\n");
					}
					logger.info(MessageConstants.DATA_FOUND_MESSAGE);
				} else {

					System.out.println(MessageConstants.NO_DATA_FOUND_MESSAGE);
					System.out.println("Exceptions occured: " + responseDtoProduct.getStatusCode());
					logger.error(MessageConstants.NO_DATA_FOUND_MESSAGE);
				}

				break;

			case 5:
				System.out.println(MessageConstants.GET_PRODUCT_MESSAGE);
				productId = scan.nextInt();
				logger.trace(MessageConstants.ENTER_FUNCTION_LOGS);
				responseDtoSkus = catalogueController.getAllSkus(productId);
				logger.trace(MessageConstants.EXITING_FUNCTION_LOGS);
				if (responseDtoSkus.getStatusCode() == StatusCodeMapping.SUCCESS_CODE) {
					
					for(ProductSku skuDto: responseDtoSkus.getData()) {
						System.out.println("Product Id: " + skuDto.getProductId() + "\nSKU id: "
								+ skuDto.getSkuId() +  "\nMRP: "+ skuDto.getMrp()+"\nSku Description: "+skuDto.getSkuDescription()+"\nSkus : "+skuDto.getSkuAttributes());
					
					}
					logger.info(MessageConstants.DATA_FOUND_MESSAGE);
				} else {

					System.out.println(MessageConstants.NO_DATA_FOUND_MESSAGE);
					System.out.println("Exceptions occured: " + responseDtoSkus.getStatusCode());
					logger.error(MessageConstants.NO_DATA_FOUND_MESSAGE);
				}

				break;

			default:
				System.out.println("Invalid");
				break;
			}

		} while (choice < 6);
		scan.close();

		logger.info("Closing the application . . . ");
	}

}
