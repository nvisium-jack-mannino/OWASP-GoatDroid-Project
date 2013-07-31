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
package org.owasp.goatdroid.webservice.herdfinancial.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.gui.emulator.Emulator;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Utils;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.dao.HFForgotPasswordDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;
import org.springframework.stereotype.Service;

@Service
public class HFForgotPasswordServiceImpl implements ForgotPasswordService {

	@Resource
	HFForgotPasswordDaoImpl dao;

	public BaseModel requestCode(String userName, int secretQuestionIndex,
			String secretQuestionAnswer) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();

		if (!Validators.validateUserNameFormat(userName))
			errors.add(Constants.USERNAME_FORMAT_INVALID);

		else if (!Validators.validateSecretQuestionIndex(Integer
				.toString(secretQuestionIndex)))
			errors.add(Constants.SECRET_QUESTION_INDEX_INVALID);

		else if (!Validators
				.validateSingleSecretQuestionAnswer(secretQuestionAnswer))
			errors.add(Constants.SECRET_QUESTION_ANSWER_TOO_LONG);

		try {
			if (errors.size() == 0) {
				if (dao.confirmSecretQuestionAnswer(userName,
						Integer.toString(secretQuestionIndex),
						secretQuestionAnswer)) {
					int token = Utils.generateSessionToken();
					dao.updatePasswordResetCode(userName,
							Integer.toString(token));
					/*
					 * We send our out-of-band password reset code straight to
					 * the device
					 */
					Emulator.sendSMSToEmulator("8885551234",
							Integer.toString(token));
					base.setSuccess(true);
				} else
					errors.add(Constants.USERNAME_FORMAT_INVALID);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}

	public BaseModel verifyCode(String userName, int passwordResetCode) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();

		if (!Validators.validateUserNameFormat(userName))
			errors.add(Constants.USERNAME_FORMAT_INVALID);

		else if (!Validators
				.validatePasswordResetTokenFormat(passwordResetCode))
			errors.add(Constants.INVALID_PASSWORD_RESET_CODE);

		try {
			if (errors.size() == 0) {
				if (dao.confirmPasswordResetCode(userName, passwordResetCode))
					base.setSuccess(true);
				else
					errors.add(Constants.INVALID_PASSWORD_RESET_CODE);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}

	public BaseModel updatePassword(String userName, int passwordResetCode,
			String password) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();

		if (!Validators.validateUserNameFormat(userName))
			errors.add(Constants.USERNAME_FORMAT_INVALID);

		else if (!Validators
				.validatePasswordResetTokenFormat(passwordResetCode))
			errors.add(Constants.INVALID_PASSWORD_RESET_CODE);

		else if (!Validators.validatePasswordLength(password))
			errors.add(Constants.INVALID_PASSWORD_LENGTH);

		try {
			if (errors.size() == 0) {
				if (dao.confirmPasswordResetCode(userName, passwordResetCode)) {
					/*
					 * If all inputs pass our validation and the code is valid
					 * for the specified user, we allow the password reset
					 * process to take place
					 */
					dao.updatePassword(userName, password);
					/*
					 * After updating the password, we then remove the code from
					 * the database to prevent additional resets without
					 * re-initiating the process properly
					 */
					dao.clearPasswordResetCode(userName);
					base.setSuccess(true);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}
}
