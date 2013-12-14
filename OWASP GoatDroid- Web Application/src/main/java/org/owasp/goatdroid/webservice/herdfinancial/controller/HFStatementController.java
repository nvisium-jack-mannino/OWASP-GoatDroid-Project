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
package org.owasp.goatdroid.webservice.herdfinancial.controller;

import javax.servlet.http.HttpServletRequest;

import org.owasp.goatdroid.webservice.herdfinancial.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFStatementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/priv/statements", produces = "application/json")
public class HFStatementController {

	@Autowired
	HFStatementServiceImpl statementService;

	@RequestMapping(value = "get-statement/{accountNumber}/{startDate}/{endDate}", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getStatement(
			@PathVariable("accountNumber") String accountNumber,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate, HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return statementService.getStatement(accountNumber, startDate,
					endDate, authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "poll-statement-updates/{accountNumber}", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getStatementSinceLastPoll(
			@PathVariable("accountNumber") String accountNumber,
			HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return statementService.getStatementSinceLastPoll(accountNumber,
					authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}
