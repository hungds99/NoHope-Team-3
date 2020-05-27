package com.javaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.javaweb.common.utils.ViewNameUtils;

/**
 * @author DINH SY HUNG
 * @version 1.0
 * @since 2020-01-05
 */

@Controller
public class HomeController {

	@GetMapping("/login")
	public String getLoginPage() {

		return ViewNameUtils.VIEW_LOGIN_PAGE;
	}
	
}
