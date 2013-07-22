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
import org.owasp.goatdroid.webservice.fourgoats.services.AdminServiceImpl;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.SecretQuestionBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.SecretQuestionServiceImpl;

@Controller
@RequestMapping("herdfinancial/api/v1/secret_questions")
public class SecretQuestionController {

	SecretQuestionServiceImpl secretQuestionService;

	@Autowired
	public SecretQuestionController(SecretQuestionServiceImpl secretQuestionService) {
		this.secretQuestionService = secretQuestionService;
	}
	
	@RequestMapping(value="set", method = RequestMethod.POST)
	public SecretQuestionBean setSecretQuestions(
			@CookieParam(Constants.SESSION_TOKEN) int sessionToken,
			@FormParam("answer1") String answer1,
			@FormParam("answer2") String answer2,
			@FormParam("answer3") String answer3) {
		try {
			return SecretQuestionServiceImpl.setSecretQuestions(sessionToken, answer1,
					answer2, answer3);
		} catch (NullPointerException e) {
			SecretQuestionBean bean = new SecretQuestionBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
