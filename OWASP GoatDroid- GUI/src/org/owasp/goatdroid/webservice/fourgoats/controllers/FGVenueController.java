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
import org.owasp.goatdroid.webservice.fourgoats.model.VenueModel;
import org.owasp.goatdroid.webservice.fourgoats.model.VenueListModel;
import org.owasp.goatdroid.webservice.fourgoats.services.FGVenueServiceImpl;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/venues", produces = "application/json")
public class FGVenueController {

	@Autowired
	FGVenueServiceImpl venueService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public VenueModel addVenue(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "venueName", required = true) String venueName,
			@RequestParam(value = "venueWebsite", required = true) String venueWebsite,
			@RequestParam(value = "latitude", required = true) String latitude,
			@RequestParam(value = "longitude", required = true) String longitude) {
		try {
			return venueService.addVenue(sessionToken, venueName, venueWebsite,
					latitude, longitude);
		} catch (NullPointerException e) {
			VenueModel bean = new VenueModel();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public VenueListModel getAllVenues(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {
		try {
			return venueService.getAllVenues(sessionToken);
		} catch (NullPointerException e) {
			VenueListModel bean = new VenueListModel();
			bean.setSuccess(false);
			return bean;
		}
	}
}
