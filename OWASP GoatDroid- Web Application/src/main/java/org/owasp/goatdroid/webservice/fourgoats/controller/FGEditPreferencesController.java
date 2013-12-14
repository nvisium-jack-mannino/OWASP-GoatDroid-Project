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
package org.owasp.goatdroid.webservice.fourgoats.controller;

import javax.servlet.http.HttpServletRequest;

import org.owasp.goatdroid.webservice.fourgoats.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.GetPreferences;
import org.owasp.goatdroid.webservice.fourgoats.model.Preferences;
import org.owasp.goatdroid.webservice.fourgoats.services.FGEditPreferencesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/preferences", produces = "application/json")
public class FGEditPreferencesController {

	@Autowired
	FGEditPreferencesServiceImpl editPreferencesService;

	@RequestMapping(value = "modify-preferences", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel modifyPreferences(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("editPreferencesModel") Preferences editPreferencesModel,
			BindingResult result) {

		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return editPreferencesService.modifyPreferences(
					authHeader.getAuthToken(),
					editPreferencesModel.isAutoCheckin(),
					editPreferencesModel.isPublic());
		} catch (NullPointerException e) {
			BaseModel base = new Preferences();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "get-preferences", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getPreferences(HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return editPreferencesService.getPreferences(authHeader
					.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new GetPreferences();
			base.setSuccess(false);
			return base;
		}
	}
}
