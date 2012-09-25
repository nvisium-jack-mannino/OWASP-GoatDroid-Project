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
package org.owasp.goatdroid.fourgoats.rest.history;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HistoryResponse {

	static public ArrayList<HashMap<String, String>> parseHistoryResponse(
			String response) {

		JSONObject json;
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
		String errors = "";

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("true")) {
				JSONArray checkinArray = json.getJSONArray("history");
				for (int count = 0; count < checkinArray.length(); count++) {
					HashMap<String, String> resultsMap = new HashMap<String, String>();
					resultsMap.put("success", "true");
					HashMap<String, String> checkin = new HashMap<String, String>();

					if (checkinArray.getJSONObject(count).has("dateTime"))
						checkin.put("dateTime", (String) checkinArray
								.getJSONObject(count).get("dateTime"));
					if (checkinArray.getJSONObject(count).has("checkinID"))
						checkin.put("checkinID", (String) checkinArray
								.getJSONObject(count).get("checkinID"));
					if (checkinArray.getJSONObject(count).has("latitude"))
						checkin.put("latitude", (String) checkinArray
								.getJSONObject(count).get("latitude"));
					if (checkinArray.getJSONObject(count).has("longitude"))
						checkin.put("longitude", (String) checkinArray
								.getJSONObject(count).get("longitude"));
					if (checkinArray.getJSONObject(count).has("venueName"))
						checkin.put("venueName", (String) checkinArray
								.getJSONObject(count).get("venueName"));
					if (checkinArray.getJSONObject(count).has("venueWebsite"))
						checkin.put("venueWebsite", (String) checkinArray
								.getJSONObject(count).get("venueWebsite"));
					results.add(resultsMap);
					if (checkin.size() > 0)
						results.add(checkin);
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
				HashMap<String, String> checkin = new HashMap<String, String>();
				if (json.getJSONObject("history").has("dateTime"))
					checkin.put(
							"dateTime",
							(String) json.getJSONObject("history").get(
									"dateTime"));
				if (json.getJSONObject("history").has("checkinID"))
					checkin.put(
							"checkinID",
							(String) json.getJSONObject("history").get(
									"checkinID"));
				if (json.getJSONObject("history").has("latitude"))
					checkin.put(
							"latitude",
							(String) json.getJSONObject("history").get(
									"latitude"));
				if (json.getJSONObject("history").has("longitude"))
					checkin.put(
							"longitude",
							(String) json.getJSONObject("history").get(
									"longitude"));
				if (json.getJSONObject("history").has("venueName"))
					checkin.put(
							"venueName",
							(String) json.getJSONObject("history").get(
									"venueName"));
				if (json.getJSONObject("history").has("venueWebsite"))
					checkin.put(
							"venueWebsite",
							(String) json.getJSONObject("history").get(
									"venueWebsite"));
				if (checkin.size() > 0)
					results.add(checkin);

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
