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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.CheckinBean;
import org.owasp.goatdroid.webservice.fourgoats.services.FGCheckinServiceImpl;

@Controller
@RequestMapping(value = "fourgoats/api/v1/checkin", produces = "application/json")
public class FGCheckinController {

	FGCheckinServiceImpl checkinService;

	@Autowired
	public FGCheckinController(FGCheckinServiceImpl checkinService) {
		this.checkinService = checkinService;
	}

	@RequestMapping(value = "thing", method = RequestMethod.POST)
	@ResponseBody
	public CheckinBean doCheckin(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "latitude", required = true) String latitude,
			@RequestParam(value = "longitude", required = true) String longitude) {
		try {
			return checkinService.doCheckin(sessionToken, latitude, longitude);
		} catch (NullPointerException e) {
			CheckinBean bean = new CheckinBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
