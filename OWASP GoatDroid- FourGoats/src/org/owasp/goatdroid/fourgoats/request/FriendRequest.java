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
 * @author Walter Tighzert
 * @created 2012
 */
package org.owasp.goatdroid.fourgoats.request;

import java.util.ArrayList;
import java.util.HashMap;

import org.owasp.goatdroid.fourgoats.http.AuthenticatedRestClient;
import org.owasp.goatdroid.fourgoats.http.RequestMethod;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.response.FriendResponse;
import org.owasp.goatdroid.fourgoats.responseobjects.Friend;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;
import org.owasp.goatdroid.fourgoats.responseobjects.PendingFriendRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.ResponseObject;

import android.content.Context;

public class FriendRequest {

	Context context;
	String destinationInfo;

	public FriendRequest(Context context) {

		destinationInfo = Utils.getDestinationInfo(context);
		this.context = context;
	}

	public GenericResponseObject doFriendRequest(String userName)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/friends/request-friend", context);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse
				.parseDoFriendRequestResponse(client.getResponse());
	}

	public Friend getProfile(String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/friends/view_profile/" + userName,
				context);
		client.Execute(RequestMethod.GET, context);

		return FriendResponse.parseProfileResponse(client.getResponse());
	}

	public PendingFriendRequest getPendingFriendRequests() throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/friends/get-pending-requests",
				context);
		client.Execute(RequestMethod.GET, context);

		return FriendResponse.parsePendingFriendRequestsResponse(client
				.getResponse());
	}

	public GenericResponseObject acceptFriendRequest(String userName)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/friends/accept-friend-request",
				context);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse.parseAcceptFriendRequestResponse(client
				.getResponse());

	}

	public ResponseObject removeFriendRequest(String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/friends/remove-friend", context);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse.parseRemoveFriendRequestResponse(client
				.getResponse());

	}

	public GenericResponseObject denyFriendRequest(String userName)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/friends/deny-friend-request", context);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse.parseDenyFriendRequestResponse(client
				.getResponse());

	}

	public ArrayList<HashMap<String, String>> getFriends() throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/friends/list-friends", context);
		client.Execute(RequestMethod.GET, context);

		return FriendResponse.parseListFriendsResponse(client.getResponse());
	}
}
