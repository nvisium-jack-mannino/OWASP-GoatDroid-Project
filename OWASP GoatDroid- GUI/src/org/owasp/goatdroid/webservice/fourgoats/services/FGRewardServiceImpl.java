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
import org.owasp.goatdroid.webservice.fourgoats.dao.FGRewardDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.RewardModel;
import org.springframework.stereotype.Service;

@Service
public class FGRewardServiceImpl implements RewardService {

	@Resource
	FGRewardDaoImpl dao;

	public RewardModel getAllRewards(String authToken) {

		RewardModel reward = new RewardModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			reward.setRewards(dao.getAllRewards());
			reward.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			reward.setErrors(errors);
		}
		return reward;
	}

	public RewardModel getMyEarnedRewards(String authToken) {

		RewardModel reward = new RewardModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			String userID = dao.getUserID(authToken);
			reward.setRewards(dao.getEarnedRewards(userID));
			reward.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			reward.setErrors(errors);
		}
		return reward;
	}

	/*
	 * This feature is only available to administrative users
	 */
	public RewardModel addNewReward(String authToken, String rewardName,
			String rewardDescription, String venueID, int checkinsRequired) {

		RewardModel reward = new RewardModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			if (!Validators.validateItemNameLength(rewardName)
					|| !Validators
							.validateDescriptionOrCommentLength(rewardDescription)
					|| !Validators.validateIDFormat(venueID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(authToken);
				// check for admin rights
				if (dao.isAdmin(userID)) {
					if (dao.doesVenueExist(venueID)) {
						String rewardID = LoginUtils.generateSaltedSHA256Hash(
								venueID + rewardName + authToken
										+ LoginUtils.getTimeMilliseconds(),
								Salts.REWARD_ID_GENERATOR_SALT);
						dao.addNewReward(rewardID, rewardName,
								rewardDescription, venueID, checkinsRequired);
						reward.setRewardID(rewardID);
						reward.setSuccess(true);
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
			reward.setErrors(errors);
		}
		return reward;
	}
}
