package com.lblin.weixin.interfaces.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
@Controller
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/login",method = GET)
	public String login(){
		return "admin/login";
	}
}
