package com.talkortell.bbs.apigate.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkortell.bbs.base.common.SessionUser;

@RestController
public class HomeController {
	@Autowired
	private HttpServletRequest request;
	
	@GetMapping("/boot")
	public String printBootState() {
		return "boot success";
	}
	
	@GetMapping("/mockLogin")
	public String mocklogin() {
		SessionUser su = new SessionUser();
		su.setUserKey(2l);
		su.setUserId("611251291447885897");
		su.setLoginAccount("wolaile@qq.com");
		
		HttpSession session = request.getSession();
		session.setAttribute("login_user", su);
		return "boot success";
	}
	
}
