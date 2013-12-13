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
package org.owasp.goatdroid.webservice.fourgoats.controllers;

import javax.servlet.http.HttpServletRequest;

import org.owasp.goatdroid.webservice.fourgoats.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.History;
import org.owasp.goatdroid.webservice.fourgoats.services.FGHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/history", produces = "application/json")
public class FGHistoryController {

	@Autowired
	FGHistoryServiceImpl historyService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getHistory(HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return historyService.getHistory(authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new History();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "get-user-history/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getHistory(HttpServletRequest request,
			@PathVariable(value = "userName") String userName) {
		try {
			return historyService.getUserHistory(userName);
		} catch (NullPointerException e) {
			BaseModel base = new History();
			base.setSuccess(false);
			return base;
		}
	}
}
