package com.mycompany.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HomeController {
	@RequestMapping("/")
	public String home() {
		log.info("실행");
		return "home";
	}

}
