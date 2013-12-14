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
import org.owasp.goatdroid.webservice.fourgoats.model.Checkin;
import org.owasp.goatdroid.webservice.fourgoats.services.FGFriendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/friends", produces = "application/json")
public class FGFriendController {

	@Autowired
	FGFriendServiceImpl friendService;

	@RequestMapping(value = "list-friends", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getFriends(HttpServletRequest request) {

		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService.getFriends(authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "request-friend", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel requestFriend(HttpServletRequest request,
			@RequestParam(value = "userName", required = true) String userName) {

		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService.requestFriend(authHeader.getAuthToken(),
					userName);
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}

	}

	@RequestMapping(value = "accept-friend-request", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel acceptFriendRequest(HttpServletRequest request,
			@RequestParam(value = "userName", required = true) String userName) {
		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService.acceptOrDenyFriendRequest("accept",
					authHeader.getAuthToken(), userName);
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}

	}

	@RequestMapping(value = "deny-friend-request", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel denyFriendRequest(HttpServletRequest request,
			@RequestParam(value = "userName", required = true) String userName) {
		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService.acceptOrDenyFriendRequest("deny",
					authHeader.getAuthToken(), userName);
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}

	}

	@RequestMapping(value = "remove-friend", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel removeFriend(HttpServletRequest request,
			@RequestParam(value = "userName", required = true) String userName) {
		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService.removeFriend(authHeader.getAuthToken(),
					userName);
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "get-pending-requests", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getPendingFriendRequests(HttpServletRequest request) {
		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService.getPendingFriendRequests(authHeader
					.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "search-users", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getPublicUsers(HttpServletRequest request) {
		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService.getPublicUsers(authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}
	}

	@RequestMapping(value = "view-profile/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel getProfile(HttpServletRequest request,
			@PathVariable(value = "userName") String userName) {
		AuthorizationHeader authHeader = (AuthorizationHeader) request
				.getAttribute("authHeader");
		try {
			return friendService
					.getProfile(authHeader.getAuthToken(), userName);
		} catch (NullPointerException e) {
			BaseModel base = new Checkin();
			base.setSuccess(false);
			return base;
		}
	}
}
