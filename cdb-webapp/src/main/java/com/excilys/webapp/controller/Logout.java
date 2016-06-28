package com.excilys.webapp.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Logout")
public class Logout {
	public String logout(HttpServletRequest httpServletRequest) {
		try {
			httpServletRequest.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:Dashboard";
	}
}
