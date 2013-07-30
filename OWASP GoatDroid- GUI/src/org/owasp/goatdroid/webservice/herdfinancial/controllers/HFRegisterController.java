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
package org.owasp.goatdroid.webservice.herdfinancial.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.herdfinancial.model.RegisterModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFRegisterServiceImpl;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/pub/register", produces = "application/json")
public class HFRegisterController {

	@Autowired
	HFRegisterServiceImpl registerService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public RegisterModel doRegistration(
			@RequestParam(value = "accountNumber", required = true) String accountNumber,
			@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {
		try {
			return registerService.registerUser(accountNumber, firstName,
					lastName, userName, password);
		} catch (NullPointerException e) {
			RegisterModel bean = new RegisterModel();
			bean.setSuccess(false);
			return bean;
		}
	}
}
