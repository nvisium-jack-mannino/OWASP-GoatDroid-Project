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

import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.Register;
import org.owasp.goatdroid.webservice.fourgoats.services.FGRegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "fourgoats/api/v1/pub/register", produces = "application/json")
public class FGRegisterController {

	@Autowired
	FGRegisterServiceImpl registerService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public BaseModel doRegistration(Model model,
			@ModelAttribute("registerModel") Register register,
			BindingResult result) {
		try {
			return registerService.registerUser(register.getFirstname(),
					register.getLastname(), register.getUsername(),
					register.getPassword());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}