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

import org.owasp.goatdroid.webservice.fourgoats.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.UserPass;
import org.owasp.goatdroid.webservice.fourgoats.services.FGAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/admin", produces = "application/json")
public class FGAdminController {

	@Autowired
	FGAdminServiceImpl adminService;

	@RequestMapping(value = "delete-user", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel deleteUser(HttpServletRequest request,
			@RequestParam(value = "username", required = true) String userName) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return adminService.deleteUser(authHeader.getAuthToken(), userName);
		} catch (NullPointerException e) {
			BaseModel model = new BaseModel();
			model.setSuccess(false);
			return model;
		}
	}

	@RequestMapping(value = "reset-password", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel resetPassword(HttpServletRequest request, Model model,
			@ModelAttribute("userPassModel") UserPass userPass,
			BindingResult result) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return adminService.resetPassword(authHeader.getAuthToken(),
					userPass.getUsername(), userPass.getPassword());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "get-users", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getUsers(HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return adminService.getUsers(authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "sign-out", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel signOut(HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return adminService.signOut(authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}