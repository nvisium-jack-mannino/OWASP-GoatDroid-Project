/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.webservice.fourgoats.controllers;

import org.owasp.goatdroid.webservice.fourgoats.model.LoginModel;
import org.owasp.goatdroid.webservice.fourgoats.model.UserPassModel;
import org.owasp.goatdroid.webservice.fourgoats.services.FGLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "fourgoats/api/v1/pub/login", produces = "application/json")
public class FGLoginController {

	@Autowired
	FGLoginServiceImpl loginService;

	@RequestMapping(value = "authenticate", method = RequestMethod.POST)
	@ResponseBody
	public LoginModel authenticate(Model model,
			@ModelAttribute("userPassModel") UserPassModel userPass,
			BindingResult result) {
		return loginService.validateCredentials(userPass.getUsername(),
				userPass.getPassword());
	}

	@RequestMapping(value = "validate-api", method = RequestMethod.POST)
	@ResponseBody
	public LoginModel validateCredentialsAPI(Model model,
			@ModelAttribute("userPassModel") UserPassModel userPass,
			BindingResult result) {
		try {
			return loginService.validateCredentialsAPI(userPass.getUsername(),
					userPass.getPassword());
		} catch (NullPointerException e) {
			LoginModel login = new LoginModel();
			login.setSuccess(false);
			return login;
		}
	}
}
