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
package org.owasp.goatdroid.webservice.fourgoats.resource;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendProfileBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PendingFriendRequestsBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PublicUsersBean;
import org.owasp.goatdroid.webservice.fourgoats.impl.Friend;

@Path("/fourgoats/api/v1/friends")
public class FriendResource {

	@Path("list_friends")
	@GET
	@Produces("application/json")
	public FriendListBean getFriends(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {

		return Friend.getFriends(sessionToken);
	}

	@Path("request_friend")
	@POST
	@Produces("application/json")
	public FriendBean requestFriend(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return Friend.requestFriend(sessionToken, userName);

	}

	@Path("accept_friend_request")
	@POST
	@Produces("application/json")
	public FriendBean acceptFriendRequest(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return Friend.acceptOrDenyFriendRequest("accept", sessionToken,
				userName);

	}

	@Path("deny_friend_request")
	@POST
	@Produces("application/json")
	public FriendBean denyFriendRequest(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return Friend.acceptOrDenyFriendRequest("deny", sessionToken, userName);

	}

	@Path("remove_friend")
	@POST
	@Produces("application/json")
	public FriendBean removeFriend(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("userName") String userName) {

		return Friend.removeFriend(sessionToken, userName);
	}

	@Path("get_pending_requests")
	@GET
	@Produces("application/json")
	public PendingFriendRequestsBean getPendingFriendRequests(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {

		return Friend.getPendingFriendRequests(sessionToken);
	}

	@Path("search_users")
	@GET
	@Produces("application/json")
	public PublicUsersBean getPublicUsers(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {

		return Friend.getPublicUsers(sessionToken);
	}

	@Path("view_profile/{userName}")
	@GET
	@Produces("application/json")
	public FriendProfileBean getProfile(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@PathParam("userName") String userName) {

		return Friend.getProfile(sessionToken, userName);
	}

}
