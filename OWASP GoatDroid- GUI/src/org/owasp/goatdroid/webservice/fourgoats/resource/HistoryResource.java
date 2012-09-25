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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.CookieParam;
import javax.ws.rs.PathParam;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.HistoryBean;
import org.owasp.goatdroid.webservice.fourgoats.impl.History;

@Path("/fourgoats/api/v1/history")
public class HistoryResource {

	@Path("list")
	@GET
	@Produces("application/json")
	public HistoryBean getHistory(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return History.getHistory(sessionToken);
		} catch (NullPointerException e) {
			HistoryBean bean = new HistoryBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("get_user_history/{userName}")
	@GET
	@Produces("application/json")
	public HistoryBean getHistory(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@PathParam("userName") String userName) {
		try {
			return History.getUserHistory(sessionToken, userName);
		} catch (NullPointerException e) {
			HistoryBean bean = new HistoryBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
