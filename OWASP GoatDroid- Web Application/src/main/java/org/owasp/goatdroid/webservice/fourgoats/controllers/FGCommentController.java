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

import org.owasp.goatdroid.webservice.fourgoats.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.Comment;
import org.owasp.goatdroid.webservice.fourgoats.services.FGCommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/comments", produces = "application/json")
public class FGCommentController {

	@Autowired
	FGCommentServiceImpl commentService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel addComment(HttpServletRequest request, Model model,
			@ModelAttribute("commentModel") Comment commentModel,
			BindingResult result) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return commentService.addComment(authHeader.getAuthToken(),
					commentModel.getComment(), commentModel.getCheckinID());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel removeComment(HttpServletRequest request, Model model,
			@ModelAttribute("commentModel") Comment commentModel,
			BindingResult result) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return commentService.removeComment(authHeader.getAuthToken(),
					commentModel.getCommentID());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "get/{checkinID}", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getComments(HttpServletRequest request, Model model,
			@ModelAttribute("commentModel") Comment commentModel,
			BindingResult result) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return commentService.getComments(authHeader.getAuthToken(),
					commentModel.getCheckinID());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}
