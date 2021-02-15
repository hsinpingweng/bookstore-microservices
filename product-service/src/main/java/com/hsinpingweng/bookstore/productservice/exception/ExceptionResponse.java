package com.hsinpingweng.bookstore.productservice.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExceptionResponse {

	private HttpStatus status;
	private Date timestamp;
	private List<String> errors;

	public ExceptionResponse(HttpStatus status, List<String> errors) {
		super();
		this.status = status;
		this.timestamp = new Date();
		this.errors = errors;
	}
//
	public ExceptionResponse(HttpStatus status, String error) {
		super();
		this.status = status;
		this.timestamp = new Date();
		errors = Arrays.asList(error);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}


	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
