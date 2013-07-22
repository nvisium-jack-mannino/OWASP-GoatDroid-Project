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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.EditPreferencesBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.GetPreferencesBean;
import org.owasp.goatdroid.webservice.fourgoats.services.AdminServiceImpl;
import org.owasp.goatdroid.webservice.fourgoats.services.EditPreferencesServiceImpl;

@Controller
@RequestMapping("fourgoats/api/v1/preferences")
public class EditPreferencesController {

	EditPreferencesServiceImpl editPreferencesService;

	@Autowired
	public EditPreferencesController(EditPreferencesServiceImpl editPreferencesService) {
		this.editPreferencesService = editPreferencesService;
	}
	
	@RequestMapping(value = "modify_preferences", method = RequestMethod.POST)
	public EditPreferencesBean modifyPreferences(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("autoCheckin") boolean autoCheckin,
			@FormParam("isPublic") boolean isPublic) {

		try {
			return EditPreferencesServiceImpl.modifyPreferences(sessionToken,
					autoCheckin, isPublic);
		} catch (NullPointerException e) {
			EditPreferencesBean bean = new EditPreferencesBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "get_preferences", method = RequestMethod.GET)
	public GetPreferencesBean getPreferences(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return EditPreferencesServiceImpl.getPreferences(sessionToken);
		} catch (NullPointerException e) {
			GetPreferencesBean bean = new GetPreferencesBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
