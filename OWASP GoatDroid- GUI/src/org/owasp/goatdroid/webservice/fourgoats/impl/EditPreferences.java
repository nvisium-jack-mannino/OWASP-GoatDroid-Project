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
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.bean.EditPreferencesBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.GetPreferencesBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.EditPreferencesDAO;

public class EditPreferences {

	static public EditPreferencesBean modifyPreferences(String sessionToken,
			boolean autoCheckin, boolean isPublic) {

		EditPreferencesBean bean = new EditPreferencesBean();
		ArrayList<String> errors = new ArrayList<String>();
		EditPreferencesDAO dao = new EditPreferencesDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				dao.updatePreferences(autoCheckin, isPublic, userID);
				bean.setSuccess(true);
				return bean;
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

	static public GetPreferencesBean getPreferences(String sessionToken) {

		GetPreferencesBean bean = new GetPreferencesBean();
		ArrayList<String> errors = new ArrayList<String>();
		EditPreferencesDAO dao = new EditPreferencesDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				bean.setPreferences(dao.getPreferences(userID));
				dao.closeConnection();
				bean.setSuccess(true);
				return bean;
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
