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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.owasp.goatdroid.webservice.herdfinancial.bean.RegisterBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.RegisterServiceImpl;

@Controller
@Path("/herdfinancial/api/v1/register")
public class RegisterController {
	@POST
	@Produces("application/json")
	public RegisterBean doRegistration(
			@FormParam("accountNumber") String accountNumber,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("userName") String userName,
			@FormParam("password") String password) {
		try {
			return RegisterServiceImpl.registerUser(accountNumber, firstName, lastName,
					userName, password);
		} catch (NullPointerException e) {
			RegisterBean bean = new RegisterBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
