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
package org.owasp.goatdroid.webservice.herdfinancial.impl;

import java.util.ArrayList;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.bean.AuthorizeBean;
import org.owasp.goatdroid.webservice.herdfinancial.dao.AuthorizeDAO;

public class Authorize {

	static public AuthorizeBean authorizeDevice(String deviceID,
			int sessionToken) {

		AuthorizeBean bean = new AuthorizeBean();
		ArrayList<String> errors = new ArrayList<String>();
		AuthorizeDAO dao = new AuthorizeDAO();

		if (!Login.isSessionValid(sessionToken))
			errors.add(Constants.SESSION_EXPIRED);
		else if (!Validators.validateDeviceID(deviceID))
			errors.add(Constants.INVALID_DEVICE_ID);

		try {
			dao.openConnection();
			if (!dao.isDeviceAuthorized(deviceID)) {
				dao.authorizeDevice(deviceID, sessionToken);
				bean.setSuccess(true);
			} else
				errors.add(Constants.DEVICE_ALREADY_AUTHORIZED);
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
