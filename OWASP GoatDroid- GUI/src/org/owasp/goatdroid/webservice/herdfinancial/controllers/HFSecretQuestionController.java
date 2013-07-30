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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.model.SecretQuestionModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFSecretQuestionServiceImpl;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/priv/secret_questions", produces = "application/json")
public class HFSecretQuestionController {

	HFSecretQuestionServiceImpl secretQuestionService;

	@Autowired
	public HFSecretQuestionController(
			HFSecretQuestionServiceImpl secretQuestionService) {
		this.secretQuestionService = secretQuestionService;
	}

	@RequestMapping(value = "set", method = RequestMethod.POST)
	@ResponseBody
	public SecretQuestionModel setSecretQuestions(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken,
			@RequestParam(value = "answer1", required = true) String answer1,
			@RequestParam(value = "answer2", required = true) String answer2,
			@RequestParam(value = "answer3", required = true) String answer3) {
		try {
			return secretQuestionService.setSecretQuestions(sessionToken,
					answer1, answer2, answer3);
		} catch (NullPointerException e) {
			SecretQuestionModel bean = new SecretQuestionModel();
			bean.setSuccess(false);
			return bean;
		}
	}
}
