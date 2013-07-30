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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.model.LoginModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFLoginServiceImpl;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/pub/login", produces = "application/json")
public class HFLoginController {

	HFLoginServiceImpl loginService;

	@Autowired
	public HFLoginController(HFLoginServiceImpl loginService) {
		this.loginService = loginService;
	}

	@RequestMapping(value = "authenticate", method = RequestMethod.POST)
	@ResponseBody
	public LoginModel submitCredentials(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {
		try {
			return loginService.validateCredentials(userName, password);
		} catch (NullPointerException e) {
			LoginModel bean = new LoginModel();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "device/{deviceID}", method = RequestMethod.GET)
	@ResponseBody
	public LoginModel checkDeviceRegistration(
			@PathVariable("deviceID") String deviceID) {
		try {
			return loginService.isDevicePermanentlyAuthorized(deviceID);
		} catch (NullPointerException e) {
			LoginModel bean = new LoginModel();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "device_or_session/{deviceID}", method = RequestMethod.GET)
	@ResponseBody
	public LoginModel checkDeviceRegistration(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken,
			@PathVariable("deviceID") String deviceID) {
		try {
			return loginService.isSessionValidOrDeviceAuthorized(sessionToken,
					deviceID);
		} catch (NullPointerException e) {
			LoginModel bean = new LoginModel();
			bean.setSuccess(false);
			return bean;
		}
	}
}
