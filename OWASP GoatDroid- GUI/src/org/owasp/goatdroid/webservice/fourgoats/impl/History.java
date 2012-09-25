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
import java.util.HashMap;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.bean.HistoryBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.HistoryCheckinBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.HistoryDAO;

public class History {

	static public HistoryBean getHistory(String sessionToken) {

		HistoryBean bean = new HistoryBean();
		ArrayList<String> errors = new ArrayList<String>();
		HistoryDAO dao = new HistoryDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				bean.setHistory(dao.getCheckinHistory(dao
						.getUserID(sessionToken)));
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

	static public HistoryCheckinBean getCheckin(String sessionToken,
			String checkinID) {

		HistoryCheckinBean bean = new HistoryCheckinBean();
		ArrayList<String> errors = new ArrayList<String>();
		HistoryDAO dao = new HistoryDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateIDFormat(checkinID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				String checkinOwner = dao.getCheckinOwner(checkinID);

				if (userID.equals(checkinOwner)
						|| dao.isProfilePublic(checkinOwner)
						|| dao.isFriend(userID, checkinOwner)) {

					bean.setVenueName(dao.getVenueName(checkinID));
					HashMap<String, String> checkinInfo = dao
							.getCheckinInfo(checkinID);
					bean.setDateTime(checkinInfo.get("dateTime"));
					bean.setVenueWebsite(dao.getVenueWebsite(checkinID));
					bean.setLatitude(checkinInfo.get("latitude"));
					bean.setLongitude(checkinInfo.get("longitude"));
					bean.setComments(dao.selectComments(checkinID));
					bean.setSuccess(true);
				}
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

	static public HistoryBean getUserHistory(String sessionToken,
			String userName) {

		HistoryBean bean = new HistoryBean();
		ArrayList<String> errors = new ArrayList<String>();
		HistoryDAO dao = new HistoryDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateUserNameFormat(userName))
				errors.add(Constants.USERNAME_FORMAT_INVALID);

			if (errors.size() == 0) {
				bean.setHistory(dao.getCheckinHistoryByUserName(userName));
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
