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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.StatementBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.RegisterServiceImpl;
import org.owasp.goatdroid.webservice.herdfinancial.services.StatementServiceImpl;

@Controller
@RequestMapping("herdfinancial/api/v1/statements")
public class StatementController {

	StatementServiceImpl statementService;

	@Autowired
	public StatementController(StatementServiceImpl statementService) {
		this.statementService = statementService;
	}
	
	@RequestMapping(value = "get_statement/{accountNumber}/{startDate}/{endDate}", method = RequestMethod.GET)
	public StatementBean getStatement(
			@PathParam("accountNumber") String accountNumber,
			@PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate,
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken) {
		try {
			return StatementServiceImpl.getStatement(accountNumber, startDate,
					endDate, sessionToken);
		} catch (NullPointerException e) {
			StatementBean bean = new StatementBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "poll_statement_updates/{accountNumber}", method = RequestMethod.GET)
	public StatementBean getStatementSinceLastPoll(
			@PathParam("accountNumber") String accountNumber,
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken) {
		try {
			return StatementServiceImpl.getStatementSinceLastPoll(
					accountNumber, sessionToken);
		} catch (NullPointerException e) {
			StatementBean bean = new StatementBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
