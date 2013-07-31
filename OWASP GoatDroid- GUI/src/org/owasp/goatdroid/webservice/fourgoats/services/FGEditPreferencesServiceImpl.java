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

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.dao.FGEditPreferencesDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.PreferencesModel;
import org.springframework.stereotype.Service;

@Service
public class FGEditPreferencesServiceImpl implements EditPreferencesService {

	@Resource
	FGEditPreferencesDaoImpl dao;

	public BaseModel modifyPreferences(String sessionToken,
			boolean autoCheckin, boolean isPublic) {

		PreferencesModel preferences = new PreferencesModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			String userID = dao.getUserID(sessionToken);
			dao.updatePreferences(autoCheckin, isPublic, userID);
			preferences.setSuccess(true);
			return preferences;
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			preferences.setErrors(errors);
		}
		return preferences;
	}

	public BaseModel getPreferences(String sessionToken) {

		PreferencesModel preferences = new PreferencesModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			String userID = dao.getUserID(sessionToken);
			HashMap<String, Boolean> preferenceMap = dao.getPreferences(userID);
			preferences.setAutoCheckin(preferenceMap.get("autoCheckin"));
			preferences.setPublic(preferenceMap.get("isPublic"));
			preferences.setSuccess(true);
			return preferences;
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			preferences.setErrors(errors);
		}
		return preferences;
	}
}
