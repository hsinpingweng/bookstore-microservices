package com.hsinpingweng.bookstore.productservice.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

//apply to all controller
@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler 
	extends ResponseEntityExceptionHandler{


	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(NotFoundException ex, WebRequest request)  {
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage());

		return new ResponseEntity(exceptionResponse, exceptionResponse.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		List<String> errors = new ArrayList<>();

		for (FieldError error : ex.getBindingResult().getFieldErrors())
			errors.add(error.getField() + " : " + error.getDefaultMessage());

		for (ObjectError error : ex.getBindingResult().getGlobalErrors())
			errors.add(error.getObjectName() + " : " + error.getDefaultMessage());

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, errors);

		return new ResponseEntity<>(exceptionResponse, headers, exceptionResponse.getStatus());
	}


	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND, error);
		return new ResponseEntity<Object>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
	}



	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Parameter " + ex.getParameterName() + " is missing";

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
	}


	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex, WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
	}


	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		String error = "Argument " + ex.getName() + " should be of type " + ex.getRequiredType().getName();

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
	}


}
