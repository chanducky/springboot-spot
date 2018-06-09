package com.Jimdo.spot.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Jimdo.spot.exception.BadRequestException;
import com.Jimdo.spot.modal.ResponseData;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "This should be application specific";
		ResponseData response = new ResponseData(HttpStatus.CONFLICT,bodyOfResponse);
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
		ResponseData response = new ResponseData(HttpStatus.BAD_REQUEST,ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,WebRequest request) {
		ResponseData response = new ResponseData(status,ex.getMessage());
		return new ResponseEntity<Object>(response, status);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		String message = "Unknow error, Please contact support team.";
		ResponseData response = new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR,message);
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
