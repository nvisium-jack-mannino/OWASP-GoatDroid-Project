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
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentBean;
import org.owasp.goatdroid.webservice.fourgoats.services.AdminServiceImpl;
import org.owasp.goatdroid.webservice.fourgoats.services.CommentServiceImpl;

@Controller
@RequestMapping("fourgoats/api/v1/comments")
public class CommentController {

	CommentServiceImpl commentService;

	@Autowired
	public CommentController(CommentServiceImpl commentService) {
		this.commentService = commentService;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public CommentBean addComment(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("comment") String comment,
			@FormParam("checkinID") String checkinID) {
		try {
			return CommentServiceImpl.addComment(sessionToken, comment,
					checkinID);
		} catch (NullPointerException e) {
			CommentBean bean = new CommentBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public CommentBean removeComment(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("commentID") String commentID) {
		try {
			return CommentServiceImpl.removeComment(sessionToken, commentID);
		} catch (NullPointerException e) {
			CommentBean bean = new CommentBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "get/{checkinID}", method = RequestMethod.GET)
	public CommentListBean getComments(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@PathParam("checkinID") String checkinID) {
		try {
			return CommentServiceImpl.getComments(sessionToken, checkinID);
		} catch (NullPointerException e) {
			CommentListBean bean = new CommentListBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
