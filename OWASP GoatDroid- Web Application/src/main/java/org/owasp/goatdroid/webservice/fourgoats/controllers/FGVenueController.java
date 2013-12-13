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

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.Comment;
import org.owasp.goatdroid.webservice.fourgoats.model.Venue;
import org.owasp.goatdroid.webservice.fourgoats.model.VenueList;
import org.owasp.goatdroid.webservice.fourgoats.services.FGVenueServiceImpl;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/venues", produces = "application/json")
public class FGVenueController {

	@Autowired
	FGVenueServiceImpl venueService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel addVenue(HttpServletRequest request, Model model,
			@ModelAttribute("venueModel") Venue venue, BindingResult result) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return venueService.addVenue(authHeader.getAuthToken(),
					venue.getVenueName(), venue.getVenueWebsite(),
					venue.getLatitude(), venue.getLongitude());
		} catch (NullPointerException e) {
			BaseModel base = new Venue();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getAllVenues(HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return venueService.getAllVenues(authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}
