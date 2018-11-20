/**
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBM Corporation 2018
 */
package com.zowe.jzos.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zowe.jzos.exception.EntryNotFoundException;
import com.zowe.jzos.model.NameValueParameter;
import com.zowe.jzos.model.NameValueResponse;
import com.zowe.jzos.model.ValueParameter;
import com.zowe.jzos.services.PropertiesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Java environment Information", tags = { "JzOS" })
@RequestMapping(value = "/jzos")
public class VariablesController {

	Logger logger = LoggerFactory.getLogger(VariablesController.class);

	@Autowired
	PropertiesService jzosService;

	@GetMapping(value = { "/environmentVariables" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Fetch Java environment variables", tags = {
			"JzOS" }, notes = "This API returns the current set of Java environment variables.")
	public ResponseEntity<List<NameValueResponse>> getEnv() {
		logger.trace("Get the environmentVariables");
		return new ResponseEntity<>(jzosService.getPropertyList(), HttpStatus.OK);
	}

	@PostMapping(value = "/environmentVariables", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Creates an environment variable with value", tags = {
			"JzOS" }, notes = "This API uses JZOS to perform the process.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Environment variable created") })
	public ResponseEntity<Object> post(@RequestBody NameValueParameter parameter
			) throws EntryNotFoundException {
		String property = jzosService.getSingleProperty(parameter.getName());
		if (null != property) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		try {
			jzosService.setProperty(parameter);
		} catch (Exception e) {
			//throw new EntryNotFoundException("attribute does not exist. Use POST to create it");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/environmentVariables/{attribute}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Updates the value of an existing environment variable", tags = {
			"JzOS" }, notes = "This API uses JZOS to perform the process.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Confirmation the environment variable was updated") })
	public ResponseEntity<Object> update(@PathVariable("attribute") String attribute,
			@RequestBody ValueParameter parameter) throws EntryNotFoundException {
		String property = jzosService.getSingleProperty(attribute);
		if (null == property) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			jzosService.updateProperty(attribute, parameter);
		} catch (Exception e) {
			//throw new EntryNotFoundException("attribute does not exist. Use POST to create it");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/environmentVariables/{attribute}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Deletes an existing environment variable", notes = "This API uses JZOS to delete the environment variable.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Environment variable deleted") })
	public ResponseEntity<Object> delete(@PathVariable("attribute") String attribute) {
		String property = jzosService.getSingleProperty(attribute);
		if (null == property) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		jzosService.deleteKey(attribute);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/environmentVariables/{attribute}" },  produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Gets a specific environment variables", notes = "This API uses JZOS to perform the process.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Ok", response = NameValueResponse.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "Environment variable does not exist") })
	public ResponseEntity<Object> getOne(@PathVariable("attribute") String attribute) {
		logger.info("Seeking "+attribute);
		String property = jzosService.getSingleProperty(attribute);
		if (null == property) {
			// throw new EntryNotFoundException(
			String error =	String.format("Attribute %s does not exist.", attribute);
			return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(new NameValueResponse(attribute, property), HttpStatus.OK);
	}

	@GetMapping("/log")
	@ApiOperation(value = "Fetch Current application Log", tags = {
			"JzOS" }, notes = "This API returns the current application log.")
	public String status() {
		String data = "";
		logger.trace("Get the log");
		try {
			data = new String(Files.readAllBytes(Paths.get("app.log")));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return data;
	}
}
