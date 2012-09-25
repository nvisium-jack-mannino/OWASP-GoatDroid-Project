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

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import org.owasp.goatdroid.webservice.herdfinancial.bean.RegisterBean;
import org.owasp.goatdroid.webservice.herdfinancial.impl.Register;

@Path("/herdfinancial/api/v1/register")
public class RegisterResource {
	@POST
	@Produces("application/json")
	public RegisterBean doRegistration(
			@FormParam("accountNumber") String accountNumber,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("userName") String userName,
			@FormParam("password") String password) {
		try {
			return Register.registerUser(accountNumber, firstName, lastName,
					userName, password);
		} catch (NullPointerException e) {
			RegisterBean bean = new RegisterBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
