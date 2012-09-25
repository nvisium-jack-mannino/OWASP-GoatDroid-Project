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
package org.owasp.goatdroid.fourgoats.rest.login;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.goatdroid.fourgoats.base.ResponseBase;

public class LoginResponse extends ResponseBase {

	static public HashMap<String, String> parseLoginResponse(String response) {
		JSONObject json;
		HashMap<String, String> results = new HashMap<String, String>();

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("false")) {
				results.put("success", "false");
				return results;
			} else {
				results.put("success", "true");
				results.put("sessionToken", json.getString("sessionToken"));
				results.put("userName", json.getString("userName"));

				JSONObject preferencesObject = json
						.getJSONObject("preferences");
				JSONArray entry = preferencesObject.getJSONArray("entry");
				for (int count = 0; count < entry.length(); count++) {
					results.put(entry.getJSONObject(count).getString("key"),
							entry.getJSONObject(count).getString("value"));
				}
				return results;
			}

		} catch (JSONException e) {
			results.put("errors", e.getMessage());
			results.put("success", "false");
			return results;
		}
	}

	static public HashMap<String, String> parseAPILoginResponse(String response) {
		JSONObject json;
		HashMap<String, String> results = new HashMap<String, String>();

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("false")) {
				results.put("success", "false");
				return results;
			} else {
				results.put("success", "true");
				results.put("sessionToken", json.getString("sessionToken"));
				return results;
			}

		} catch (JSONException e) {
			results.put("success", "false");
			return results;
		}
	}
}
