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
import org.owasp.goatdroid.webservice.fourgoats.bean.RewardBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.RewardDAO;

public class Reward {

	static public RewardBean getAllRewards(String sessionToken) {

		RewardBean bean = new RewardBean();
		ArrayList<String> errors = new ArrayList<String>();
		RewardDAO dao = new RewardDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				bean.setRewards(dao.getAllRewards());
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

	static public RewardBean getMyEarnedRewards(String sessionToken) {

		RewardBean bean = new RewardBean();
		ArrayList<String> errors = new ArrayList<String>();
		RewardDAO dao = new RewardDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				bean.setRewards(dao.getEarnedRewards(userID));
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

	/*
	 * This feature is only available to administrative users
	 */
	static public RewardBean addNewReward(String sessionToken,
			String rewardName, String rewardDescription, String venueID,
			int checkinsRequired) {

		RewardBean bean = new RewardBean();
		ArrayList<String> errors = new ArrayList<String>();
		RewardDAO dao = new RewardDAO();

		try {
			dao.openConnection();
			if (!Validators.validateItemNameLength(rewardName)
					|| !Validators
							.validateDescriptionOrCommentLength(rewardDescription)
					|| !Validators.validateIDFormat(venueID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				// check for admin rights
				if (dao.isAdmin(userID)) {
					if (dao.doesVenueExist(venueID)) {
						String rewardID = LoginUtils.generateSaltedSHA256Hash(
								venueID + rewardName + sessionToken
										+ LoginUtils.getTimeMilliseconds(),
								Salts.REWARD_ID_GENERATOR_SALT);
						dao.addNewReward(rewardID, rewardName,
								rewardDescription, venueID, checkinsRequired);
						bean.setRewardID(rewardID);
						bean.setSuccess(true);
					} else {
						errors.add(Constants.VENUE_DOESNT_EXIST);
					}
				} else {
					errors.add(Constants.NOT_AUTHORIZED);
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
