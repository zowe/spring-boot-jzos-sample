/**
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBM Corporation 2018
 */
package com.zowe.jzos.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.ibm.jzos.ZUtil;
import com.zowe.jzos.model.NameValueParameter;
import com.zowe.jzos.model.NameValueResponse;
import com.zowe.jzos.model.ValueParameter;

@Configuration
public class PropertiesService {

	Logger log = LoggerFactory.getLogger(PropertiesService.class);
	
	public PropertiesService() {}

	public List<NameValueResponse> getPropertyList() {
		List<NameValueResponse> pairs = new ArrayList<NameValueResponse>();
		try {
			for (String element:ZUtil.environ()) {
				String[] tokens = element.split(" |=");
				pairs.add(new NameValueResponse(tokens[0],tokens[1]));
			}
		} catch (Exception e) {
			String error = String.format("Attempt to read environment list failed: %s", e.getMessage());
			log.warn(error);
		}
		return pairs;
	}
	
	public String getSingleProperty(String attribute) {
		log.info("Requesting "+ attribute);
		String value = null;
		try {
			value =  ZUtil.getEnv(attribute);
		} catch (Exception e) {
			String error = String.format("Attempt to read environment %s failed: %s", attribute, e.getMessage());
			log.warn(error);
		}
		if (value==null) {
			String error = String.format("Environment variable %s not found", attribute); 
			log.warn(error);
		}
		log.info("returning "+ value);
		return value;
	}
	
	public List<NameValueResponse> getProperty(String attribute) {
		List<NameValueResponse> pairs = new ArrayList<NameValueResponse>();
		String value = null;
		try {
			value =  ZUtil.getEnv(attribute);
		} catch (Exception e) {
			String error = String.format("Attempt to read environment %s failed: %s", attribute, e.getMessage());
			log.warn(error);
		}
		if (value==null) {
			String error = String.format("Environment variable %s not found", attribute); 
			log.warn(error);
		}
		pairs.add(new NameValueResponse(attribute,value));
		return pairs;
	}

	public List<NameValueResponse> setProperty(NameValueParameter request) {
		List<NameValueResponse> pairs = new ArrayList<NameValueResponse>();
		if (ZUtil.getEnv(request.getName())!=null) {
			log.warn("The attribute already exists. Use PUT to update it"); //$NON-NLS-1$
		}
		if (request.getValue() != null ) {
			ZUtil.setEnv(request.getName(),request.getValue());
			pairs.add(new NameValueResponse(request.getName(), request.getValue()));
		} else {
			log.warn("Value equates to null. No action taken"); //$NON-NLS-1$
		}
		return pairs;
	}

	public void updateProperty(String attribute, ValueParameter value) {
		if (ZUtil.getEnv(attribute)==null) {
			log.warn("The attribute does not exist. Use POST to create it"); //$NON-NLS-1$
		}
		if (value.getValue() != null ) {
			//System.out.println("value.getValue() "+value.getValue());
			ZUtil.setEnv(attribute,value.getValue());
		} else {
			log.warn("Value equates to null. No action taken"); //$NON-NLS-1$
		} 
	}
	public void deleteKey(String attribute) {
		if (ZUtil.getEnv(attribute)==null) {
			log.warn("The attribute does not exist anyway. No further action"); //$NON-NLS-1$
		}
		ZUtil.setEnv(attribute, null);
}
}