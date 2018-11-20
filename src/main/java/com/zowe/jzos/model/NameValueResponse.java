/**
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBM Corporation 2018
 */
package com.zowe.jzos.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NameValueResponse {

	private String name;
	private String value;

	public NameValueResponse(String attr, String val) {
		name = attr;
		value = val;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}	
}