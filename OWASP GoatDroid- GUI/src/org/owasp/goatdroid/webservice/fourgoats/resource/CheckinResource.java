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

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.CookieParam;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.CheckinBean;
import org.owasp.goatdroid.webservice.fourgoats.impl.Checkin;

@Path("/fourgoats/api/v1/checkin")
public class CheckinResource {

	@POST
	@Produces("application/json")
	public CheckinBean doCheckin(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("latitude") String latitude,
			@FormParam("longitude") String longitude) {
		try {
			return Checkin.doCheckin(sessionToken, latitude, longitude);
		} catch (NullPointerException e) {
			CheckinBean bean = new CheckinBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
