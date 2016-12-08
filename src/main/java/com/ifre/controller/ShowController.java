package com.ifre.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Scope("prototype")
@Controller
@RequestMapping("/showController")
public class ShowController {

	@RequestMapping(params = "showAPIFqz")
	public ModelAndView showAPIFqz(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/apiFqz");
	}
	
	@RequestMapping(params = "showAPIWy")
	public ModelAndView showAPIWy(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/apiWy");
	}
	
	@RequestMapping(params = "showAPI")
	public ModelAndView showAPI(HttpServletRequest request) {
		return new ModelAndView("com/ifre/show/api");
	}

    
	
	
}
