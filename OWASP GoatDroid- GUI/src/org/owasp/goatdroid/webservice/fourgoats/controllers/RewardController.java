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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.RewardBean;
import org.owasp.goatdroid.webservice.fourgoats.services.RewardServiceImpl;

@Controller
@Path("/fourgoats/api/v1/rewards")
public class RewardController {

	@Path("all_rewards")
	@GET
	@Produces("application/json")
	public RewardBean getRewards(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return RewardServiceImpl.getAllRewards(sessionToken);
		} catch (NullPointerException e) {
			RewardBean bean = new RewardBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("my_rewards")
	@GET
	@Produces("application/json")
	public RewardBean getMyEarnedRewards(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return RewardServiceImpl.getMyEarnedRewards(sessionToken);
		} catch (NullPointerException e) {
			RewardBean bean = new RewardBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("add")
	@POST
	@Produces("application/json")
	public RewardBean addNewReward(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("rewardName") String rewardName,
			@FormParam("rewardDescription") String rewardDescription,
			@FormParam("venueID") String venueID,
			@FormParam("checkinsRequired") int checkinsRequired) {

		try {
			return RewardServiceImpl.addNewReward(sessionToken, rewardName,
					rewardDescription, venueID, checkinsRequired);
		} catch (NullPointerException e) {
			RewardBean bean = new RewardBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
