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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentBean;
import org.owasp.goatdroid.webservice.fourgoats.services.CommentServiceImpl;

@Controller
@RequestMapping(value = "fourgoats/api/v1/comments", produces = "application/json")
public class CommentController {

	CommentServiceImpl commentService;

	@Autowired
	public CommentController(CommentServiceImpl commentService) {
		this.commentService = commentService;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public CommentBean addComment(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "comment", required = true) String comment,
			@RequestParam(value = "checkinID", required = true) String checkinID) {
		try {
			return commentService.addComment(sessionToken, comment, checkinID);
		} catch (NullPointerException e) {
			CommentBean bean = new CommentBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public CommentBean removeComment(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "commentID", required = true) String commentID) {
		try {
			return commentService.removeComment(sessionToken, commentID);
		} catch (NullPointerException e) {
			CommentBean bean = new CommentBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@RequestMapping(value = "get/{checkinID}", method = RequestMethod.GET)
	@ResponseBody
	public CommentListBean getComments(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@PathVariable(value = "checkinID") String checkinID) {
		try {
			return commentService.getComments(sessionToken, checkinID);
		} catch (NullPointerException e) {
			CommentListBean bean = new CommentListBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
