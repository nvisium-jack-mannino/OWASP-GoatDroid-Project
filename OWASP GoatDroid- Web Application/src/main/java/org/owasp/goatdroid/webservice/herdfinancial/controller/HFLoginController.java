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
package org.owasp.goatdroid.webservice.herdfinancial.controller;

import javax.servlet.http.HttpServletRequest;

import org.owasp.goatdroid.webservice.herdfinancial.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/pub/login", produces = "application/json")
public class HFLoginController {

	@Autowired
	HFLoginServiceImpl loginService;

	@RequestMapping(value = "authenticate", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel submitCredentials(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {
		try {
			return loginService.validateCredentials(userName, password);
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "device/{deviceID}", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel checkDeviceRegistration(
			@PathVariable("deviceID") String deviceID) {
		try {
			return loginService.isDevicePermanentlyAuthorized(deviceID);
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "device-or-auth/{deviceID}", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel checkDeviceRegistration(HttpServletRequest request,
			@PathVariable("deviceID") String deviceID) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return loginService.isAuthValidOrDeviceAuthorized(
					authHeader.getAuthToken(), deviceID);
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}
