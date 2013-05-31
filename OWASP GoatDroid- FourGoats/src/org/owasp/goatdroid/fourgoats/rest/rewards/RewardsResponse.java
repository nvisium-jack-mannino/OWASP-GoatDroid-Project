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
package org.owasp.goatdroid.fourgoats.rest.rewards;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.goatdroid.fourgoats.base.ResponseBase;

public class RewardsResponse extends ResponseBase {

	static public ArrayList<HashMap<String, String>> parseRewardsResponse(
			String response) {

		JSONObject json;
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
		String errors = "";

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("true")) {
				JSONArray rewardArray = json.getJSONArray("rewards");
				for (int count = 0; count < rewardArray.length(); count++) {
					HashMap<String, String> resultsMap = new HashMap<String, String>();
					resultsMap.put("success", "true");
					HashMap<String, String> reward = new HashMap<String, String>();
					if (rewardArray.getJSONObject(count).has("rewardName"))
						reward.put("rewardName", (String) rewardArray
								.getJSONObject(count).get("rewardName"));
					if (rewardArray.getJSONObject(count).has(
							"rewardDescription"))
						reward.put("rewardDescription", (String) rewardArray
								.getJSONObject(count).get("rewardDescription"));
					if (rewardArray.getJSONObject(count).has("venueName"))
						reward.put("venueName", (String) rewardArray
								.getJSONObject(count).get("venueName"));
					if (rewardArray.getJSONObject(count)
							.has("checkinsRequired"))
						reward.put("checkinsRequired", (String) rewardArray
								.getJSONObject(count).get("checkinsRequired"));
					if (rewardArray.getJSONObject(count).has("latitude"))
						reward.put("latitude", (String) rewardArray
								.getJSONObject(count).get("latitude"));
					if (rewardArray.getJSONObject(count).has("longitude"))
						reward.put("longitude", (String) rewardArray
								.getJSONObject(count).get("longitude"));
					if (rewardArray.getJSONObject(count).has("timeEarned"))
						reward.put("timeEarned", (String) rewardArray
								.getJSONObject(count).get("timeEarned"));
					results.add(resultsMap);
					if (reward.size() > 0)
						results.add(reward);
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
				HashMap<String, String> reward = new HashMap<String, String>();
				if (json.getJSONObject("rewards").has("rewardName"))
					reward.put(
							"rewardName",
							(String) json.getJSONObject("rewards").get(
									"rewardName"));
				if (json.getJSONObject("rewards").has("rewardDescription"))
					reward.put("rewardDescription", (String) json
							.getJSONObject("rewards").get("rewardDescription"));
				if (json.getJSONObject("rewards").has("venueName"))
					reward.put(
							"venueName",
							(String) json.getJSONObject("rewards").get(
									"venueName"));
				if (json.getJSONObject("rewards").has("checkinsRequired"))
					reward.put(
							"checkinsRequired",
							(String) json.getJSONObject("rewards").get(
									"checkinsRequired"));
				if (json.getJSONObject("rewards").has("latitude"))
					reward.put(
							"latitude",
							(String) json.getJSONObject("rewards").get(
									"latitude"));
				if (json.getJSONObject("rewards").has("longitude"))
					reward.put(
							"longitude",
							(String) json.getJSONObject("rewards").get(
									"longitude"));
				if (json.getJSONObject("rewards").has("timeEarned"))
					reward.put(
							"timeEarned",
							(String) json.getJSONObject("rewards").get(
									"timeEarned"));
				results.add(resultsMap);
				if (reward.size() > 0)
					results.add(reward);

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
