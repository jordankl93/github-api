package com.trustly.githubapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.trustly.githubapi.exception.BusinessException;
import com.trustly.githubapi.model.response.GenericError;

@RestControllerAdvice
public class ValidatorExceptionHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<GenericError> handleMethodArgumentNotValidException(MissingServletRequestParameterException exception) {
		GenericError errorDetails = new GenericError(exception.getParameterName(), exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<GenericError> handleBusinessException(BusinessException exception) {		
		return new ResponseEntity<>(exception.getErro(), HttpStatus.NOT_FOUND);		
	}


	@ExceptionHandler(Exception.class)
	public final ResponseEntity<GenericError> handleAllExceptions(Exception ex, WebRequest request) {
		GenericError errorDetails = new GenericError("Internal error", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}