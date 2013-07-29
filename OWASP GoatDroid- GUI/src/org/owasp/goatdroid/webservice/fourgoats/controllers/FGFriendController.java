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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendProfileBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PendingFriendRequestsBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PublicUsersBean;
import org.owasp.goatdroid.webservice.fourgoats.services.FGFriendServiceImpl;

@Controller
@RequestMapping(value = "fourgoats/api/v1/priv/friends", produces = "application/json")
public class FGFriendController {

	FGFriendServiceImpl friendService;

	@Autowired
	public FGFriendController(FGFriendServiceImpl friendService) {
		this.friendService = friendService;
	}

	@RequestMapping(value = "list_friends", method = RequestMethod.GET)
	@ResponseBody
	public FriendListBean getFriends(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {

		return friendService.getFriends(sessionToken);
	}

	@RequestMapping(value = "request_friend", method = RequestMethod.POST)
	@ResponseBody
	public FriendBean requestFriend(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "userName", required = true) String userName) {

		return friendService.requestFriend(sessionToken, userName);

	}

	@RequestMapping(value = "accept_friend_request", method = RequestMethod.POST)
	@ResponseBody
	public FriendBean acceptFriendRequest(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "userName", required = true) String userName) {

		return friendService.acceptOrDenyFriendRequest("accept", sessionToken,
				userName);

	}

	@RequestMapping(value = "deny_friend_request", method = RequestMethod.POST)
	@ResponseBody
	public FriendBean denyFriendRequest(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "userName", required = true) String userName) {

		return friendService.acceptOrDenyFriendRequest("deny", sessionToken,
				userName);

	}

	@RequestMapping(value = "remove_friend", method = RequestMethod.POST)
	@ResponseBody
	public FriendBean removeFriend(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@RequestParam(value = "userName", required = true) String userName) {

		return friendService.removeFriend(sessionToken, userName);
	}

	@RequestMapping(value = "get_pending_requests", method = RequestMethod.GET)
	@ResponseBody
	public PendingFriendRequestsBean getPendingFriendRequests(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {

		return friendService.getPendingFriendRequests(sessionToken);
	}

	@RequestMapping(value = "search_users", method = RequestMethod.GET)
	@ResponseBody
	public PublicUsersBean getPublicUsers(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken) {

		return friendService.getPublicUsers(sessionToken);
	}

	@RequestMapping(value = "view_profile/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public FriendProfileBean getProfile(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) String sessionToken,
			@PathVariable(value = "userName") String userName) {

		return friendService.getProfile(sessionToken, userName);
	}

}
