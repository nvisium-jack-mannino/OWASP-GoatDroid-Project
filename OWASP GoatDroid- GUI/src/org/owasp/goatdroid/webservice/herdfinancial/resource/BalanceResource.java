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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.CookieParam;
import javax.ws.rs.PathParam;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.BalanceBean;
import org.owasp.goatdroid.webservice.herdfinancial.impl.Balance;

@Path("/herdfinancial/api/v1/balances")
public class BalanceResource {
	@GET
	@Path("{accountNumber}")
	@Produces("application/json")
	public BalanceBean getBalances(
			@PathParam("accountNumber") String accountNumber,
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken) {
		try {
			return Balance.getBalances(accountNumber, sessionToken);
		} catch (NullPointerException e) {
			BalanceBean bean = new BalanceBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
