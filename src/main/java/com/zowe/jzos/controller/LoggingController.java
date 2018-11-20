package com.zowe.jzos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoggingController {

	public String def() {
		return "Jzos index location";
	}
}
