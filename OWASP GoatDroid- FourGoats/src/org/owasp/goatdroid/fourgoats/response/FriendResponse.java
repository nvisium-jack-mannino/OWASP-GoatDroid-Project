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
package org.owasp.goatdroid.fourgoats.response;

import org.owasp.goatdroid.fourgoats.responseobjects.Friend;
import org.owasp.goatdroid.fourgoats.responseobjects.FriendList;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;
import org.owasp.goatdroid.fourgoats.responseobjects.PendingFriendRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.ResponseObject;

public class FriendResponse extends BaseResponse {

	static public Friend parseProfileResponse(String response) {
		return (Friend) parseJsonResponse(response, Friend.class);
	}

	static public PendingFriendRequest parsePendingFriendRequestsResponse(
			String response) {
		return (PendingFriendRequest) parseJsonResponse(response,
				PendingFriendRequest.class);
	}

	static public GenericResponseObject parseDoFriendRequestResponse(
			String response) {
		return parseJsonResponse(response, GenericResponseObject.class);
	}

	static public GenericResponseObject parseAcceptFriendRequestResponse(
			String response) {
		return parseJsonResponse(response, GenericResponseObject.class);
	}

	static public ResponseObject parseRemoveFriendRequestResponse(
			String response) {
		return parseJsonResponse(response, GenericResponseObject.class);
	}

	static public GenericResponseObject parseDenyFriendRequestResponse(
			String response) {
		return parseJsonResponse(response, GenericResponseObject.class);
	}

	static public FriendList parseDenyFriendRequestResponseparseListFriendsResponse(
			String response) {
		return (FriendList) parseJsonResponse(response,
				GenericResponseObject.class);
	}
	
	static public FriendList parseListFriendsResponse(
			String response) {
		return (FriendList) parseJsonResponse(response,
				GenericResponseObject.class);
	}

}
