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
package org.owasp.goatdroid.fourgoats.base;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.goatdroid.fourgoats.misc.Constants;

public class ResponseBase {

	static public boolean isSuccess(String response) throws JSONException {
		JSONObject json = new JSONObject(response);
		if (json.getString("success").equals("false"))
			return false;
		else
			return true;
	}

	static public HashMap<String, String> parseStatusAndErrors(String response) {

		JSONObject json;
		HashMap<String, String> results = new HashMap<String, String>();
		String errors = "";

		try {
			if (response == null) {
				errors += Constants.COULD_NOT_CONNECT;
				results.put("success", "false");
			} else {
				json = new JSONObject(response);
				if (json.getString("success").equals("false")) {
					results.put("success", "false");
					try {
						JSONArray errorArray = json.getJSONArray("errors");

						for (int count = 0; count < errorArray.length(); count++)
							errors += "-"
									+ errorArray.getString(count).toString()
									+ "\n\n";

					} catch (JSONException e) {
						errors += json.getString("errors");
					}
				} else {
					results.put("success", "true");
				}
			}
		} catch (JSONException e) {
			results.put("success", "false");
		} catch (Exception e) {
			results.put("success", "false");
		} finally {
			results.put("errors", errors);
		}
		return results;
	}
}
