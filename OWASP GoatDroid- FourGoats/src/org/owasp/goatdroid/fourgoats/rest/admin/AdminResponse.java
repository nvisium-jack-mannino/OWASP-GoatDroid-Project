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
package org.owasp.goatdroid.fourgoats.rest.admin;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.goatdroid.fourgoats.base.ResponseBase;

public class AdminResponse extends ResponseBase {

	static public ArrayList<HashMap<String, String>> parseGetUsersResponse(
			String response) {

		JSONObject json;
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
		String errors = "";

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("true")) {
				JSONArray requestArray = json.getJSONArray("users");
				for (int count = 0; count < requestArray.length(); count++) {
					HashMap<String, String> resultsMap = new HashMap<String, String>();
					resultsMap.put("success", "true");
					HashMap<String, String> request = new HashMap<String, String>();

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

				if (json.getJSONObject("users").has("userName"))
					request.put("userName", (String) json
							.getJSONObject("users").get("userName"));
				if (json.getJSONObject("users").has("firstName"))
					request.put(
							"firstName",
							(String) json.getJSONObject("users").get(
									"firstName"));
				if (json.getJSONObject("users").has("lastName"))
					request.put("lastName", (String) json
							.getJSONObject("users").get("lastName"));
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
}
