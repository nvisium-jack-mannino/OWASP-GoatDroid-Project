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
package org.owasp.goatdroid.webservice.fourgoats.services;

import java.sql.SQLException;
import java.util.ArrayList;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.dao.FGLoginDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class FGLoginServiceImpl implements LoginService {

	@Autowired
	FGLoginDaoImpl dao;

	public LoginModel validateCredentials(String userName, String password) {

		LoginModel bean = new LoginModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			if (!Validators.validateCredentials(userName, password))
				errors.add(Constants.LOGIN_CREDENTIALS_INVALID);

			if (errors.size() == 0) {
				if (dao.validateCredentials(userName, password)) {

					String userNameAndTime = userName
							+ LoginUtils.getCurrentDateTime();
					long sessionStartTime = LoginUtils.getTimeMilliseconds();
					String sessionToken = LoginUtils.generateSaltedSHA512Hash(
							userNameAndTime, Salts.SESSION_TOKEN_SALT);
					dao.updateSessionInformation(userName, sessionToken,
							sessionStartTime);
					bean.setPreferences(dao.getPreferences(userName));
					bean.setUsername(userName);
					bean.setAuthToken(sessionToken);
					bean.setSuccess(true);
				} else
					errors.add(Constants.LOGIN_CREDENTIALS_INVALID);
			}
		} catch (DataAccessException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);

		}
		return bean;
	}

	public LoginModel validateCredentialsAPI(String userName, String password) {

		LoginModel bean = new LoginModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			if (!Validators.validateCredentials(userName, password))
				errors.add(Constants.LOGIN_CREDENTIALS_INVALID);

			if (errors.size() == 0) {
				if (dao.validateCredentials(userName, password)) {
					if (dao.getAuthToken(userName).isEmpty()) {
						String userNameAndTime = userName
								+ LoginUtils.getCurrentDateTime();
						long sessionStartTime = LoginUtils
								.getTimeMilliseconds();
						String sessionToken = LoginUtils
								.generateSaltedSHA512Hash(userNameAndTime,
										Salts.SESSION_TOKEN_SALT);
						dao.updateSessionInformation(userName, sessionToken,
								sessionStartTime);
						bean.setAuthToken(sessionToken);
					} else
						bean.setAuthToken(dao.getAuthToken(userName));
					bean.setSuccess(true);
				} else
					errors.add(Constants.LOGIN_CREDENTIALS_INVALID);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}

	public boolean validateAuthToken(String userName, String authToken) {

		try {
			if (dao.isAuthValid(userName, authToken))
				return true;
			else
				return false;
		} catch (Exception e) {
			/*
			 * Some exception handling here
			 */
			return false;
		}
	}
}
