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
package org.owasp.goatdroid.fourgoats.rest.friends;

import java.util.ArrayList;
import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.requestresponse.AuthenticatedRestClient;
import org.owasp.goatdroid.fourgoats.requestresponse.RequestMethod;

import android.content.Context;

public class FriendRequest {

	Context context;
	String destinationInfo;

	public FriendRequest(Context context) {

		destinationInfo = Utils.getDestinationInfo(context);
		this.context = context;
	}

	public HashMap<String, String> doFriendRequest(String sessionToken,
			String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/friends/request_friend",
				sessionToken);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse.parseStatusAndErrors(client.getResponse());
	}

	public HashMap<String, String> getProfile(String sessionToken,
			String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/friends/view_profile/"
				+ userName, sessionToken);
		client.Execute(RequestMethod.GET, context);

		return FriendResponse.parseProfileResponse(client.getResponse());
	}

	public ArrayList<HashMap<String, String>> getPendingFriendRequests(
			String sessionToken) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/friends/get_pending_requests",
				sessionToken);
		client.Execute(RequestMethod.GET, context);

		return FriendResponse.parsePendingFriendRequestsResponse(client
				.getResponse());
	}

	public HashMap<String, String> acceptFriendRequest(String sessionToken,
			String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/friends/accept_friend_request",
				sessionToken);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse.parseStatusAndErrors(client.getResponse());

	}

	public HashMap<String, String> removeFriendRequest(String sessionToken,
			String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/friends/remove_friend",
				sessionToken);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse.parseStatusAndErrors(client.getResponse());

	}

	public HashMap<String, String> denyFriendRequest(String sessionToken,
			String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/friends/deny_friend_request", sessionToken);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return FriendResponse.parseStatusAndErrors(client.getResponse());

	}

	public ArrayList<HashMap<String, String>> getFriends(String sessionToken)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/friends/list_friends",
				sessionToken);
		client.Execute(RequestMethod.GET, context);

		return FriendResponse.parseListFriendsResponse(client.getResponse());
	}
}
