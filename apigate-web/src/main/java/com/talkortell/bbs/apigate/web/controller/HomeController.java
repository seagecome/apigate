package com.talkortell.bbs.apigate.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/boot")
	public String printBootState() {
		return "boot success";
	}
	
}
