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
import org.owasp.goatdroid.webservice.fourgoats.bean.CheckinBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.CheckinDAO;

public class Checkin {

	static public CheckinBean doCheckin(String sessionToken, String latitude,
			String longitude) {

		CheckinBean bean = new CheckinBean();
		ArrayList<String> errors = new ArrayList<String>();
		CheckinDAO dao = new CheckinDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateCheckinFields(latitude, longitude))
				errors.add(Constants.LATITUDE_FORMAT_INVALID);

			if (errors.size() == 0) {
				if (dao.doesVenueExist(latitude, longitude)) {
					String userID = dao.getUserID(sessionToken);
					// this creates a unique checkin identifier
					String checkinID = LoginUtils
							.generateSaltedSHA256Hash(
									latitude + longitude
											+ LoginUtils.getTimeMilliseconds()
											+ userID,
									Salts.VENUE_ID_GENERATOR_SALT);
					String venueID = dao.getVenueID(latitude, longitude);
					String dateTime = LoginUtils.getCurrentDateTime();
					dao.insertCheckin(dateTime, latitude, longitude, userID,
							venueID, checkinID);
					int totalCheckins = dao.getTotalCheckins(userID);
					dao.updateUserInfo(latitude, longitude, dateTime,
							totalCheckins, userID);
					if (dao.doesVenueHaveReward(venueID)) {
						String rewardID = dao.getRewardID(venueID);
						if (dao.getCheckinsAtVenue(userID, venueID) >= dao
								.getRewardCheckinsRequired(rewardID)) {
							// check to make sure user hasn't already earned
							// reward
							if (!dao.doesUserHaveReward(userID, rewardID)) {
								dao.addReward(userID, rewardID);
								bean.setRewardEarned(dao.getRewardInfo(
										rewardID, venueID));
							}
						}
					}
					bean.setCheckinID(checkinID);
					bean.setDateTime(dateTime);
					bean.setVenueName(dao.getVenueName(venueID));
					bean.setLatitude(latitude);
					bean.setLongitude(longitude);
					bean.setSuccess(true);
				} else {
					errors.add(Constants.VENUE_DOESNT_EXIST);
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
}
