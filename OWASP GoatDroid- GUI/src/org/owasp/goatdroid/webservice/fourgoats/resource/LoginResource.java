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
package org.owasp.goatdroid.webservice.fourgoats.resource;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.LoginBean;
import org.owasp.goatdroid.webservice.fourgoats.impl.Login;

@Path("/fourgoats/api/v1/login")
public class LoginResource {

	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public LoginBean validateCredentials(
			@FormParam("userName") String userName,
			@FormParam("password") String password)
			throws WebApplicationException {
		try {
			return Login.validateCredentials(userName, password);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("validate_api")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public LoginBean validateCredentialsAPI(
			@FormParam("userName") String userName,
			@FormParam("password") String password)
			throws WebApplicationException {
		try {
			return Login.validateCredentialsAPI(userName, password);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("check_session")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public LoginBean checkSession(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return Login.checkSession(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("sign_out")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public LoginBean signOut(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return Login.signOut(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
