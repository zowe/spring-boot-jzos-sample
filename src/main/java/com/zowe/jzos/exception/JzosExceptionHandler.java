package com.zowe.jzos.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
//@RestController
public class JzosExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(EntryNotFoundException.class)
	  public final ResponseEntity<Object> handleUserNotFound(EntryNotFoundException exc, ErrorDetails detail) {
	    return new ResponseEntity<>(detail, HttpStatus.NOT_FOUND);
	  }
	
//	  @ExceptionHandler(Exception.class)
//	  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, String request) {
//	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
//	        request);
//	    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//	  }
	

}
