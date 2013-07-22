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
package org.owasp.goatdroid.webservice.herdfinancial.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.BalanceBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.BalanceServiceImpl;

@Controller
@Path("/herdfinancial/api/v1/balances")
public class BalanceController {
	@GET
	@Path("{accountNumber}")
	@Produces("application/json")
	public BalanceBean getBalances(
			@PathParam("accountNumber") String accountNumber,
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken) {
		try {
			return BalanceServiceImpl.getBalances(accountNumber, sessionToken);
		} catch (NullPointerException e) {
			BalanceBean bean = new BalanceBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
