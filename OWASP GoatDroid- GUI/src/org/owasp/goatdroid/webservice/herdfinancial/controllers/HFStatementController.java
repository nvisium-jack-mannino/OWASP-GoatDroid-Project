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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.StatementBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFStatementServiceImpl;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/statements", produces = "application/json")
public class HFStatementController {

	HFStatementServiceImpl statementService;

	@Autowired
	public HFStatementController(HFStatementServiceImpl statementService) {
		this.statementService = statementService;
	}

	@RequestMapping(value = "get_statement/{accountNumber}/{startDate}/{endDate}", method = RequestMethod.GET)
	@ResponseBody
	public StatementBean getStatement(
			@PathVariable("accountNumber") String accountNumber,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate,
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken) {
		try {
			return statementService.getStatement(accountNumber, startDate,
					endDate, sessionToken);
		} catch (NullPointerException e) {
			StatementBean bean = new StatementBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "poll_statement_updates/{accountNumber}", method = RequestMethod.GET)
	@ResponseBody
	public StatementBean getStatementSinceLastPoll(
			@PathVariable("accountNumber") String accountNumber,
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken) {
		try {
			return statementService.getStatementSinceLastPoll(accountNumber,
					sessionToken);
		} catch (NullPointerException e) {
			StatementBean bean = new StatementBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
