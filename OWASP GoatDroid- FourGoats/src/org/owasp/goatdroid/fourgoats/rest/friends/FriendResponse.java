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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.goatdroid.fourgoats.base.ResponseBase;
import org.owasp.goatdroid.fourgoats.misc.Constants;

public class FriendResponse extends ResponseBase {

	static public HashMap<String, String> parseProfileResponse(String response) {

		JSONObject json;
		HashMap<String, String> results = new HashMap<String, String>();
		String errors = "";

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("false")) {
				results.put("success", "false");
				try {
					JSONArray errorArray = json.getJSONArray("errors");

					for (int count = 0; count < errorArray.length(); count++)
						errors += errorArray.getString(count).toString()
								+ "\n\n";

				} catch (JSONException e) {
					errors += json.getString("errors");
				}

				results.put("errors", errors);
				return results;

			} else {
				results.put("success", "true");

				JSONObject checkinObject = json.getJSONObject("profile");
				JSONArray entry = checkinObject.getJSONArray("entry");
				for (int count = 0; count < entry.length(); count++) {
					results.put(entry.getJSONObject(count).getString("key"),
							entry.getJSONObject(count).getString("value"));
				}

				return results;
			}

		} catch (JSONException e) {
			results.put("success", "false");
			results.put("errors", Constants.WEIRD_ERROR);
			return results;
		}
	}

	static public ArrayList<HashMap<String, String>> parsePendingFriendRequestsResponse(
			String response) {

		JSONObject json;
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
		String errors = "";

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("true")) {
				JSONArray requestArray = json
						.getJSONArray("pendingFriendRequests");
				for (int count = 0; count < requestArray.length(); count++) {
					HashMap<String, String> resultsMap = new HashMap<String, String>();
					resultsMap.put("success", "true");
					HashMap<String, String> request = new HashMap<String, String>();

					if (requestArray.getJSONObject(count).has("requestId"))
						request.put("requestId", (String) requestArray
								.getJSONObject(count).get("requestId"));
					if (requestArray.getJSONObject(count).has("userName"))
						request.put("userName", (String) requestArray
								.getJSONObject(count).get("userName"));
					if (requestArray.getJSONObject(count).has("firstName"))
						request.put("firstName", (String) requestArray
								.getJSONObject(count).get("firstName"));
					if (requestArray.getJSONObject(count).has("lastName"))
						request.put("lastName", (String) requestArray
								.getJSONObject(count).get("lastName"));
					results.add(resultsMap);
					if (request.size() > 0)
						results.add(request);
				}
			} else {
				HashMap<String, String> resultsMap = new HashMap<String, String>();
				resultsMap.put("success", "false");
				try {
					JSONArray errorArray = json.getJSONArray("errors");

					for (int count = 0; count < errorArray.length(); count++)
						errors += errorArray.getString(count).toString()
								+ "\n\n";

				} catch (JSONException e) {
					errors += json.getString("errors");
				}
				resultsMap.put("errors", errors);
				results.add(resultsMap);
			}
		} catch (JSONException e) {
			try {
				json = new JSONObject(response);
				HashMap<String, String> resultsMap = new HashMap<String, String>();
				resultsMap.put("success", "true");
				results.add(resultsMap);
				HashMap<String, String> request = new HashMap<String, String>();
				if (json.getJSONObject("pendingFriendRequests")
						.has("requestID"))
					request.put("requestID",
							(String) json
									.getJSONObject("pendingFriendRequests")
									.get("requestID"));
				if (json.getJSONObject("pendingFriendRequests").has("userName"))
					request.put("userName",
							(String) json
									.getJSONObject("pendingFriendRequests")
									.get("userName"));
				if (json.getJSONObject("pendingFriendRequests")
						.has("firstName"))
					request.put("firstName",
							(String) json
									.getJSONObject("pendingFriendRequests")
									.get("firstName"));
				if (json.getJSONObject("pendingFriendRequests").has("lastName"))
					request.put("lastName",
							(String) json
									.getJSONObject("pendingFriendRequests")
									.get("lastName"));
				if (request.size() > 0)
					results.add(request);

			} catch (JSONException e1) {
				/*
				 * We don't care if it falls through here.
				 */
				e1.getMessage();
			}
		}
		return results;
	}

	static public ArrayList<HashMap<String, String>> parseListFriendsResponse(
			String response) {

		JSONObject json;
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
		String errors = "";

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("true")) {
				JSONArray friendArray = json.getJSONArray("friends");
				for (int count = 0; count < friendArray.length(); count++) {
					HashMap<String, String> resultsMap = new HashMap<String, String>();
					resultsMap.put("success", "true");
					HashMap<String, String> friend = new HashMap<String, String>();

					if (friendArray.getJSONObject(count).has("userID"))
						friend.put("userID", (String) friendArray
								.getJSONObject(count).get("userID"));
					if (friendArray.getJSONObject(count).has("userName"))
						friend.put("userName", (String) friendArray
								.getJSONObject(count).get("userName"));
					if (friendArray.getJSONObject(count).has("firstName"))
						friend.put("firstName", (String) friendArray
								.getJSONObject(count).get("firstName"));
					if (friendArray.getJSONObject(count).has("lastName"))
						friend.put("lastName", (String) friendArray
								.getJSONObject(count).get("lastName"));
					results.add(resultsMap);
					if (friend.size() > 0)
						results.add(friend);
				}
			} else {
				HashMap<String, String> resultsMap = new HashMap<String, String>();
				resultsMap.put("success", "false");
				try {
					JSONArray errorArray = json.getJSONArray("errors");

					for (int count = 0; count < errorArray.length(); count++)
						errors += errorArray.getString(count).toString()
								+ "\n\n";

				} catch (JSONException e) {
					errors += json.getString("errors");
				}
				resultsMap.put("errors", errors);
				results.add(resultsMap);
			}
		} catch (JSONException e) {
			try {
				json = new JSONObject(response);
				HashMap<String, String> resultsMap = new HashMap<String, String>();
				resultsMap.put("success", "true");
				results.add(resultsMap);
				HashMap<String, String> friend = new HashMap<String, String>();
				if (json.getJSONObject("friends").has("userID"))
					friend.put("userID", (String) json.getJSONObject("friends")
							.get("userID"));
				if (json.getJSONObject("friends").has("userName"))
					friend.put(
							"userName",
							(String) json.getJSONObject("friends").get(
									"userName"));
				if (json.getJSONObject("friends").has("firstName"))
					friend.put(
							"firstName",
							(String) json.getJSONObject("friends").get(
									"firstName"));
				if (json.getJSONObject("friends").has("lastName"))
					friend.put(
							"lastName",
							(String) json.getJSONObject("friends").get(
									"lastName"));
				if (friend.size() > 0)
					results.add(friend);

			} catch (JSONException e1) {
				/*
				 * We don't care if it falls through here.
				 */
				e1.getMessage();
			}
		}
		return results;
	}
}
