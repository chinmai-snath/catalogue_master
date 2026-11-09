package com.litmus7.cataloguemanagement.exceptions;

public class CatalogueDaoException extends Exception{
	private static final long serialVersionUID = 1L;
	public String errorCode;

	 public CatalogueDaoException(String message, String errorCode, Throwable cause) {
		 super(message,cause);
		 this.errorCode=errorCode;
		 
	 }
	
}
