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
package org.owasp.goatdroid.webservice.herdfinancial.resource;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.LoginBean;
import org.owasp.goatdroid.webservice.herdfinancial.impl.Login;

@Path("/herdfinancial/api/v1/login")
public class LoginResource {
	@Path("authenticate")
	@POST
	@Produces("application/json")
	public LoginBean submitCredentials(@FormParam("userName") String userName,
			@FormParam("password") String password) {
		try {
			return Login.validateCredentials(userName, password);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("device/{deviceID}")
	@GET
	@Produces("application/json")
	public LoginBean checkDeviceRegistration(
			@PathParam("deviceID") String deviceID) {
		try {
			return Login.isDevicePermanentlyAuthorized(deviceID);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("device_or_session/{deviceID}")
	@GET
	@Produces("application/json")
	public LoginBean checkDeviceRegistration(
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken,
			@PathParam("deviceID") String deviceID) {
		try {
			return Login.isSessionValidOrDeviceAuthorized(sessionToken,
					deviceID);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("sign_out")
	@GET
	@Produces("application/json")
	public LoginBean signOut(
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken) {
		try {
			return Login.signOut(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
