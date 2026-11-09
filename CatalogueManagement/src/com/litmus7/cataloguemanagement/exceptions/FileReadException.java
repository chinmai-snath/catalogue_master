package com.litmus7.cataloguemanagement.exceptions;

public class FileReadException extends RuntimeException{

	private static final long serialVersionUID = 1L;


	public FileReadException(String message, Throwable cause) {
		super(message,cause);
	}
}
