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

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.AdminBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.GetUsersAdminBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.LoginBean;
import org.owasp.goatdroid.webservice.fourgoats.services.FGAdminServiceImpl;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/admin", produces = "application/json")
public class FGAdminController {

	FGAdminServiceImpl adminService;

	@Autowired
	public FGAdminController(FGAdminServiceImpl adminService) {
		this.adminService = adminService;
	}

	@RequestMapping(value = "delete_user", method = RequestMethod.POST)
	@ResponseBody
	public AdminBean addComment(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "userName", required = true) String userName) {
		try {
			return adminService.deleteUser(sessionToken, userName);
		} catch (NullPointerException e) {
			AdminBean bean = new AdminBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "reset_password", method = RequestMethod.POST)
	@ResponseBody
	public AdminBean addComment(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "newPassword", required = true) String newPassword) {
		try {
			return adminService.resetPassword(sessionToken, userName,
					newPassword);
		} catch (NullPointerException e) {
			AdminBean bean = new AdminBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "get_users", method = RequestMethod.GET)
	@ResponseBody
	public GetUsersAdminBean addComment(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {
		try {
			return adminService.getUsers(sessionToken);
		} catch (NullPointerException e) {
			GetUsersAdminBean bean = new GetUsersAdminBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "sign_out", method = RequestMethod.POST)
	@ResponseBody
	public LoginBean signOut(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {
		try {
			return adminService.signOut(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}