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

import javax.servlet.http.HttpServletRequest;

import org.owasp.goatdroid.webservice.herdfinancial.model.AuthorizationHeaderModel;
import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFSecretQuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/priv/secret_questions", produces = "application/json")
public class HFSecretQuestionController {

	@Autowired
	HFSecretQuestionServiceImpl secretQuestionService;

	@RequestMapping(value = "set", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel setSecretQuestions(HttpServletRequest request,
			@RequestParam(value = "answer1", required = true) String answer1,
			@RequestParam(value = "answer2", required = true) String answer2,
			@RequestParam(value = "answer3", required = true) String answer3) {
		try {
			AuthorizationHeaderModel authHeader = (AuthorizationHeaderModel) request
					.getAttribute("authHeader");
			return secretQuestionService.setSecretQuestions(
					authHeader.getAuthToken(), answer1, answer2, answer3);
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}
