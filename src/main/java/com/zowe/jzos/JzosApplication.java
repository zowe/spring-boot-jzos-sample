/**
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBM Corporation 2018
 */
package com.zowe.jzos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JzosApplication {


	Logger logger = LoggerFactory.getLogger(JzosApplication.class);
	
	public JzosApplication() {
		super();
		logger.info("Started");
	}

	public static void main(String[] args) {
		SpringApplication.run(JzosApplication.class, args);
	}
}
