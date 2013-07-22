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
import org.owasp.goatdroid.webservice.herdfinancial.bean.ForgotPasswordBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.ForgotPasswordServiceImpl;

@Controller
@Path("/herdfinancial/api/v1/forgot_password")
public class ForgotPasswordController {

	@Path("request_code")
	@POST
	@Produces("application/json")
	public ForgotPasswordBean requestCode(
			@FormParam("userName") String userName,
			@FormParam("secretQuestionIndex") int secretQuestionIndex,
			@FormParam("secretQuestionAnswer") String secretQuestionAnswer) {
		try {
			return ForgotPasswordServiceImpl.requestCode(userName, secretQuestionIndex,
					secretQuestionAnswer);
		} catch (NullPointerException e) {
			ForgotPasswordBean bean = new ForgotPasswordBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("verify_code")
	@POST
	@Produces("application/json")
	public ForgotPasswordBean verifyCode(
			@FormParam("userName") String userName,
			@FormParam("passwordResetCode") int passwordResetCode) {
		try {
			return ForgotPasswordServiceImpl.verifyCode(userName, passwordResetCode);
		} catch (NullPointerException e) {
			ForgotPasswordBean bean = new ForgotPasswordBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("update_password")
	@POST
	@Produces("application/json")
	public ForgotPasswordBean getBalances(
			@FormParam("userName") String userName,
			@FormParam("passwordResetCode") int passwordResetCode,
			@FormParam("password") String password) {
		try {
			return ForgotPasswordServiceImpl.updatePassword(userName, passwordResetCode,
					password);
		} catch (NullPointerException e) {
			ForgotPasswordBean bean = new ForgotPasswordBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
