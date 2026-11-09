package com.litmus7.cataloguemanagement.dto;

import com.litmus7.cataloguemanagement.constants.StatusCodeMapping;

public class Response<T> {
		private String statusCode;
		private String errorCode;
		private String message;
		private T data;
		
		public Response(String statusCode, String errorCode, String message) {
			this.statusCode = statusCode;
			this.message = message;
			this.errorCode = errorCode;
		}
		
		public Response(String statusCode, String message, T data) {
			this.statusCode = statusCode;
			this.message = message;
			this.data = data;
		}
	 
		public String getStatusCode() {
			return statusCode;
		}
	 
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
	 
		public String getMessage() {
			return message;
		}
	 
		public void setMessage(String message) {
			this.message = message;
		}
	 
		public T getData() {
			return data;
		}
	 
		public void setData(T data) {
			this.data = data;
		}
	 
		public String getErrorCode() {
			return errorCode;
		}
	 
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		
		public boolean isSuccess() {
			if(this.statusCode == StatusCodeMapping.SUCCESS_CODE) return true;
			else return false;
		}
	}
