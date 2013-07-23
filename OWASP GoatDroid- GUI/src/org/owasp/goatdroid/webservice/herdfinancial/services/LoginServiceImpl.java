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

import org.owasp.goatdroid.webservice.fourgoats.dao.AdminDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.bean.LoginBean;
import org.owasp.goatdroid.webservice.herdfinancial.dao.LoginDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

	LoginDaoImpl dao;

	public LoginServiceImpl() {
		dao = new LoginDaoImpl();
	}

	public boolean isSessionValid(int sessionToken) {

		if (!Validators.validateSessionTokenFormat(sessionToken))
			return false;

		boolean success = false;

		try {
			long sessionStart = dao.getSessionStartTime(sessionToken);
			if (Utils.getTimeMilliseconds() - sessionStart < Constants.MILLISECONDS_MONTH) {
				success = true;
			}
		} catch (Exception e) {

		} finally {
			try {
			} catch (Exception e) {
			}
		}
		return success;
	}

	public LoginBean isSessionValidOrDeviceAuthorized(int sessionToken,
			String deviceID) {

		LoginBean bean = new LoginBean();
		ArrayList<String> errors = new ArrayList<String>();
		if (!Validators.validateDeviceID(deviceID))
			errors.add(Constants.INVALID_DEVICE_ID);

		try {
			if (errors.size() == 0) {
				if (isSessionValid(sessionToken)) {
					bean.setSuccess(true);
				} else {
					if (dao.isDevicePermanentlyAuthorized(deviceID)) {
						int newSessionToken = Utils.generateSessionToken();
						dao.updateAuthorizedDeviceSession(deviceID,
								newSessionToken, Utils.getTimeMilliseconds());
						bean.setSessionToken(newSessionToken);
						bean.setUserName(dao.getUserName(newSessionToken));
						bean.setAccountNumber(dao
								.getAccountNumber(newSessionToken));
						bean.setSuccess(true);
					}
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}

	public LoginBean isDevicePermanentlyAuthorized(String deviceID) {

		LoginBean bean = new LoginBean();
		ArrayList<String> errors = new ArrayList<String>();

		if (!Validators.validateDeviceID(deviceID))
			errors.add(Constants.INVALID_DEVICE_ID);

		try {
			if (errors.size() == 0) {
				if (dao.isDevicePermanentlyAuthorized(deviceID)) {
					int sessionToken = Utils.generateSessionToken();
					dao.updateAuthorizedDeviceSession(deviceID, sessionToken,
							Utils.getTimeMilliseconds());
					bean.setSessionToken(sessionToken);
					bean.setSuccess(true);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}

	public LoginBean validateCredentials(String userName, String password) {

		LoginBean bean = new LoginBean();
		ArrayList<String> errors = Validators.validateCredentials(userName,
				password);

		try {
			if (errors.size() == 0) {
				if (dao.validateCredentials(userName, password)) {
					int sessionToken = Utils.generateSessionToken();
					dao.updateSession(userName, sessionToken,
							Utils.getTimeMilliseconds());
					bean.setSessionToken(sessionToken);
					bean.setUserName(dao.getUserName(sessionToken));
					bean.setAccountNumber(dao.getAccountNumber(sessionToken));
					bean.setSuccess(true);
				} else
					errors.add(Constants.INVALID_CREDENTIALS);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}

	public LoginBean signOut(int sessionToken) {

		LoginBean bean = new LoginBean();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			if (isSessionValid(sessionToken)) {
				dao.terminateSession(sessionToken);
				bean.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}
}