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
package org.owasp.goatdroid.webservice.fourgoats.impl;

import java.util.ArrayList;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.bean.LoginBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.LoginDAO;

public class Login {

	static public LoginBean validateCredentials(String userName, String password) {

		LoginBean bean = new LoginBean();
		ArrayList<String> errors = new ArrayList<String>();
		LoginDAO dao = new LoginDAO();

		try {
			if (!Validators.validateCredentials(userName, password))
				errors.add(Constants.LOGIN_CREDENTIALS_INVALID);

			if (errors.size() == 0) {
				dao.openConnection();
				if (dao.validateCredentials(userName, password)) {

					String userNameAndTime = userName
							+ LoginUtils.getCurrentDateTime();
					long sessionStartTime = LoginUtils.getTimeMilliseconds();
					String sessionToken = LoginUtils.generateSaltedSHA512Hash(
							userNameAndTime, Salts.SESSION_TOKEN_SALT);
					dao.updateSessionInformation(userName, sessionToken,
							sessionStartTime);
					bean.setPreferences(dao.getPreferences(userName));
					bean.setUserName(userName);
					bean.setSessionToken(sessionToken);
					bean.setSuccess(true);
				} else
					errors.add(Constants.LOGIN_CREDENTIALS_INVALID);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public LoginBean validateCredentialsAPI(String userName,
			String password) {

		LoginBean bean = new LoginBean();
		ArrayList<String> errors = new ArrayList<String>();
		LoginDAO dao = new LoginDAO();

		try {
			if (!Validators.validateCredentials(userName, password))
				errors.add(Constants.LOGIN_CREDENTIALS_INVALID);

			if (errors.size() == 0) {
				dao.openConnection();
				if (dao.validateCredentials(userName, password)) {
					if (dao.getSessionToken(userName).isEmpty()) {
						String userNameAndTime = userName
								+ LoginUtils.getCurrentDateTime();
						long sessionStartTime = LoginUtils
								.getTimeMilliseconds();
						String sessionToken = LoginUtils
								.generateSaltedSHA512Hash(userNameAndTime,
										Salts.SESSION_TOKEN_SALT);
						dao.updateSessionInformation(userName, sessionToken,
								sessionStartTime);
						bean.setSessionToken(sessionToken);
					} else
						bean.setSessionToken(dao.getSessionToken(userName));
					bean.setSuccess(true);
				} else
					errors.add(Constants.LOGIN_CREDENTIALS_INVALID);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public LoginBean checkSession(String sessionToken) {

		LoginBean bean = new LoginBean();
		LoginDAO dao = new LoginDAO();
		try {
			dao.openConnection();
			if (dao.isSessionValid(sessionToken))
				bean.setSuccess(true);
			else
				bean.setSuccess(false);
		} catch (Exception e) {
			bean.setSuccess(false);
		}
		return bean;
	}

	static public LoginBean signOut(String sessionToken) {

		LoginBean bean = new LoginBean();
		ArrayList<String> errors = new ArrayList<String>();
		LoginDAO dao = new LoginDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				dao.terminateSession(sessionToken);
				bean.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}
}
