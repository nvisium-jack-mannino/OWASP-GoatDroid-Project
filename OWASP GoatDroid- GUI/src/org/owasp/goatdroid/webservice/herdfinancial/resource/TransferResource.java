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
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.TransferBean;
import org.owasp.goatdroid.webservice.herdfinancial.impl.Transfer;

@Path("/herdfinancial/api/v1/transfer")
public class TransferResource {
	@POST
	@Produces("application/json")
	public TransferBean doTransfer(@FormParam("from") String from,
			@FormParam("to") String to, @FormParam("amount") double amount,
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken) {

		try {
			return Transfer.transferFunds(sessionToken, from, to, amount);
		} catch (NullPointerException e) {
			TransferBean bean = new TransferBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
