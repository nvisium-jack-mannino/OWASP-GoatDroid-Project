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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendProfileBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PendingFriendRequestsBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PublicUsersBean;
import org.owasp.goatdroid.webservice.fourgoats.services.FriendServiceImpl;

@Controller
@RequestMapping("fourgoats/api/v1/friends")
public class FriendController {

	@RequestMapping(value = "list_friends", method = RequestMethod.GET)
	public FriendListBean getFriends(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {

		return FriendServiceImpl.getFriends(sessionToken);
	}

	@RequestMapping(value = "request_friend", method = RequestMethod.POST)
	public FriendBean requestFriend(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return FriendServiceImpl.requestFriend(sessionToken, userName);

	}

	@RequestMapping(value = "accept_friend_request", method = RequestMethod.POST)
	public FriendBean acceptFriendRequest(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return FriendServiceImpl.acceptOrDenyFriendRequest("accept",
				sessionToken, userName);

	}

	@RequestMapping(value = "deny_friend_request", method = RequestMethod.POST)
	public FriendBean denyFriendRequest(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return FriendServiceImpl.acceptOrDenyFriendRequest("deny",
				sessionToken, userName);

	}

	@RequestMapping(value = "remove_friend", method = RequestMethod.POST)
	public FriendBean removeFriend(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return FriendServiceImpl.removeFriend(sessionToken, userName);
	}

	@RequestMapping(value = "get_pending_requests", method = RequestMethod.GET)
	public PendingFriendRequestsBean getPendingFriendRequests(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {

		return FriendServiceImpl.getPendingFriendRequests(sessionToken);
	}

	@RequestMapping(value = "search_users", method = RequestMethod.GET)
	public PublicUsersBean getPublicUsers(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {

		return FriendServiceImpl.getPublicUsers(sessionToken);
	}

	@RequestMapping(value = "view_profile/{userName}", method = RequestMethod.GET)
	public FriendProfileBean getProfile(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@PathParam("userName") String userName) {

		return FriendServiceImpl.getProfile(sessionToken, userName);
	}

}
