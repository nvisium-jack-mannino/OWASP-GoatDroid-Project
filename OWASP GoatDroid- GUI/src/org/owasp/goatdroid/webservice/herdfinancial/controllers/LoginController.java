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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.owasp.goatdroid.webservice.fourgoats.services.AdminServiceImpl;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.LoginBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.LoginServiceImpl;

@Controller
@RequestMapping("herdfinancial/api/v1/login")
public class LoginController {
	
	LoginServiceImpl loginService;

	@Autowired
	public LoginController(LoginServiceImpl loginService) {
		this.loginService = loginService;
	}
	
	@RequestMapping(value="authenticate", method = RequestMethod.POST)
	public LoginBean submitCredentials(@FormParam("userName") String userName,
			@FormParam("password") String password) {
		try {
			return LoginServiceImpl.validateCredentials(userName, password);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value="device/{deviceID}", method = RequestMethod.GET)
	public LoginBean checkDeviceRegistration(
			@PathParam("deviceID") String deviceID) {
		try {
			return LoginServiceImpl.isDevicePermanentlyAuthorized(deviceID);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value="device_or_session/{deviceID}", method = RequestMethod.GET)
	public LoginBean checkDeviceRegistration(
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken,
			@PathParam("deviceID") String deviceID) {
		try {
			return LoginServiceImpl.isSessionValidOrDeviceAuthorized(sessionToken,
					deviceID);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value= "sign_out", method = RequestMethod.GET)
	public LoginBean signOut(
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken) {
		try {
			return LoginServiceImpl.signOut(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
