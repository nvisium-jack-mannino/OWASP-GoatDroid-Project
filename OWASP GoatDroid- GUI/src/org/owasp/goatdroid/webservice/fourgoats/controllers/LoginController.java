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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.LoginBean;
import org.owasp.goatdroid.webservice.fourgoats.services.LoginServiceImpl;

@Controller
@Path("/fourgoats/api/v1/login")
public class LoginController {

	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public LoginBean validateCredentials(
			@FormParam("username") String userName,
			@FormParam("password") String password)
			throws WebApplicationException {
		try {
			return LoginServiceImpl.validateCredentials(userName, password);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("validate_api")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public LoginBean validateCredentialsAPI(
			@FormParam("username") String userName,
			@FormParam("password") String password)
			throws WebApplicationException {
		try {
			return LoginServiceImpl.validateCredentialsAPI(userName, password);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("check_session")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public LoginBean checkSession(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return LoginServiceImpl.checkSession(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("sign_out")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public LoginBean signOut(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return LoginServiceImpl.signOut(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
