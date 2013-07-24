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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.AuthorizeBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.AuthorizeServiceImpl;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/authorize", produces = "application/json")
public class AuthorizeController {

	AuthorizeServiceImpl authorizeService;

	@Autowired
	public AuthorizeController(AuthorizeServiceImpl authorizeService) {
		this.authorizeService = authorizeService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeBean authorizeDevice(
			@RequestParam(value = "deviceID", required = true) String deviceID,
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken) {
		try {
			return authorizeService.authorizeDevice(deviceID, sessionToken);
		} catch (NullPointerException e) {
			AuthorizeBean bean = new AuthorizeBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
