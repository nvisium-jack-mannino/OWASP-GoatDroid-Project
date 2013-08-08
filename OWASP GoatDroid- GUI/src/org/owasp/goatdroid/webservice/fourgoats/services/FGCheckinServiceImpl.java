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

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.dao.FGCheckinDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.Checkin;
import org.springframework.stereotype.Service;

@Service
public class FGCheckinServiceImpl implements CheckinService {

	@Resource
	FGCheckinDaoImpl dao;

	public Checkin doCheckin(String authToken, String latitude,
			String longitude) {

		Checkin checkin = new Checkin();
		ArrayList<String> errors = new ArrayList<String>();

		try {

			if (!Validators.validateCheckinFields(latitude, longitude))
				errors.add(Constants.LATITUDE_FORMAT_INVALID);

			if (errors.size() == 0) {
				if (dao.doesVenueExist(latitude, longitude)) {
					String userID = dao.getUserID(authToken);
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
								checkin.setRewardEarned(dao.getRewardInfo(
										rewardID, venueID));
							}
						}
					}
					checkin.setCheckinID(checkinID);
					checkin.setDateTime(dateTime);
					checkin.setVenueName(dao.getVenueName(venueID));
					checkin.setLatitude(latitude);
					checkin.setLongitude(longitude);
					checkin.setSuccess(true);
				} else {
					errors.add(Constants.VENUE_DOESNT_EXIST);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			checkin.setErrors(errors);
		}
		return checkin;
	}
}
