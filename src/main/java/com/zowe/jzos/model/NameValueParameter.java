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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(Include.NON_NULL)
@ApiModel(value = "NameValueParameter", description = "Body representing the name and value of an environment variable")
public class NameValueParameter {
	@ApiModelProperty(value = "The name of the environment variable", dataType = "java.lang.String", required = true, example = "TEST_HOME")
	private String name;	
	@ApiModelProperty(value = "The value to be assigned to the environment variable", dataType = "java.lang.String", required = true, example = "/java/J8.0_64")
	private String value;
	
}