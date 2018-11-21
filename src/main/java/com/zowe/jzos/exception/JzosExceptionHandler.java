package com.zowe.jzos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class JzosExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(EntryNotFoundException.class)
	  public final ResponseEntity<Object> handleUserNotFound(EntryNotFoundException exc, ErrorDetails detail) {
	    return new ResponseEntity<>(detail, HttpStatus.NOT_FOUND);
	  }
}
