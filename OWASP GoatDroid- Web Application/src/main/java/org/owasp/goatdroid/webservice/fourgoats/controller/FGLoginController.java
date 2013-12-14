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
package org.owasp.goatdroid.webservice.fourgoats.controller;

import javax.servlet.http.HttpServletRequest;

import org.owasp.goatdroid.webservice.fourgoats.model.Login;
import org.owasp.goatdroid.webservice.fourgoats.model.UserPass;
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
	public Login authenticate(HttpServletRequest request, Model model,
			@ModelAttribute("userPassModel") UserPass userPass,
			BindingResult result) {
		try {

			return loginService.validateCredentials(userPass.getUsername(),
					userPass.getPassword());
		} catch (NullPointerException e) {
			Login login = new Login();
			login.setSuccess(false);
			return login;
		}
	}

	@RequestMapping(value = "validate-api", method = RequestMethod.POST)
	@ResponseBody
	public Login validateCredentialsAPI(Model model,
			@ModelAttribute("userPassModel") UserPass userPass,
			BindingResult result) {
		try {
			return loginService.validateCredentialsAPI(userPass.getUsername(),
					userPass.getPassword());
		} catch (NullPointerException e) {
			Login login = new Login();
			login.setSuccess(false);
			return login;
		}
	}
}
