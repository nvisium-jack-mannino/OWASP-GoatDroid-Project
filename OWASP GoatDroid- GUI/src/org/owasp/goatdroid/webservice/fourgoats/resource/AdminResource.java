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
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.AdminBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.GetUsersAdminBean;
import org.owasp.goatdroid.webservice.fourgoats.impl.Admin;

@Path("/fourgoats/api/v1/admin")
public class AdminResource {

	@Path("delete_user")
	@POST
	@Produces("application/json")
	public AdminBean addComment(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {
		try {
			return Admin.deleteUser(sessionToken, userName);
		} catch (NullPointerException e) {
			AdminBean bean = new AdminBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("reset_password")
	@POST
	@Produces("application/json")
	public AdminBean addComment(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName,
			@FormParam("newPassword") String newPassword) {
		try {
			return Admin.resetPassword(sessionToken, userName, newPassword);
		} catch (NullPointerException e) {
			AdminBean bean = new AdminBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("get_users")
	@GET
	@Produces("application/json")
	public GetUsersAdminBean addComment(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return Admin.getUsers(sessionToken);
		} catch (NullPointerException e) {
			GetUsersAdminBean bean = new GetUsersAdminBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}