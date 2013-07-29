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
package org.owasp.goatdroid.webservice.fourgoats.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.RewardBean;
import org.owasp.goatdroid.webservice.fourgoats.services.FGRewardServiceImpl;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/rewards", produces = "application/json")
public class FGRewardController {

	FGRewardServiceImpl rewardService;

	@Autowired
	public FGRewardController(FGRewardServiceImpl rewardService) {
		this.rewardService = rewardService;
	}

	@RequestMapping(value = "all_rewards", method = RequestMethod.GET)
	@ResponseBody
	public RewardBean getRewards(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {
		try {
			return rewardService.getAllRewards(sessionToken);
		} catch (NullPointerException e) {
			RewardBean bean = new RewardBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "my_rewards", method = RequestMethod.GET)
	@ResponseBody
	public RewardBean getMyEarnedRewards(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {
		try {
			return rewardService.getMyEarnedRewards(sessionToken);
		} catch (NullPointerException e) {
			RewardBean bean = new RewardBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public RewardBean addNewReward(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "rewardName", required = true) String rewardName,
			@RequestParam(value = "rewardDescription", required = true) String rewardDescription,
			@RequestParam(value = "venueID", required = true) String venueID,
			@RequestParam(value = "checkinsRequired", required = true) int checkinsRequired) {

		try {
			return rewardService.addNewReward(sessionToken, rewardName,
					rewardDescription, venueID, checkinsRequired);
		} catch (NullPointerException e) {
			RewardBean bean = new RewardBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
