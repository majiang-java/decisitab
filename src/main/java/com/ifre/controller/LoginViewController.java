package com.ifre.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Scope("prototype")
@Controller
@RequestMapping("/loginFrontController")
public class LoginViewController {

	@RequestMapping(params = "login")
	public ModelAndView loginView(HttpServletRequest request) {
		System.out.println(123);
		return new ModelAndView("model/login");
	}

    
	
	
}
