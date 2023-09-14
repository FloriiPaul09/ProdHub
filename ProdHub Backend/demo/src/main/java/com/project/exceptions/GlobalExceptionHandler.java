package com.project.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.payload.ErrorDetails;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityExistsException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> dataIntegrityException(DataIntegrityViolationException e){
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MyAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(MyAPIException exception,
																WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), 
													exception.getMessage(),
													webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
																WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(),
													exception.getMessage(),
													webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDenieidException(AccessDeniedException exception,
																		WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(),
													exception.getMessage(),
													webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
		
	}
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> manageEntityNotFoundException(EntityNotFoundException exception){
		
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<String> manageEntityExistsException(EntityExistsException exception){
		
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FOUND);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
																	HttpHeaders headers,
																	HttpStatusCode status,
																	WebRequest webRequest){
		
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) ->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		
	}
}
