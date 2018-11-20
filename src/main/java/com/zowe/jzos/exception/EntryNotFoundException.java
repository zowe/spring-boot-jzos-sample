package com.zowe.jzos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryNotFoundException extends Exception {

	private static final long serialVersionUID = -3455624904801760504L;

	public EntryNotFoundException(Exception e, String attribute) {
		super(e);	
	}

}
