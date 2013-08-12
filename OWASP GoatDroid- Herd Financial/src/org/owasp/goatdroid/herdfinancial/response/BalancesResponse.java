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
package org.owasp.goatdroid.herdfinancial.response;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BalancesResponse {

	static public HashMap<String, String> parseStatusAndBalances(String response) {

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
						errors += "-" + errorArray.getString(count).toString()
								+ "\n\n";

				} catch (JSONException e) {
					errors += "-" + json.getString("errors");
				}

				results.put("errors", errors);
				return results;

			} else {
				results.put("success", "true");
				results.put("checkingBalance",
						json.getString("checkingBalance"));
				results.put("savingsBalance", json.getString("savingsBalance"));
				return results;
			}

		} catch (JSONException e) {
			results.put("success", "false");
			results.put("errors", e.getMessage());
			return results;
		} catch (Exception e) {
			results.put("errors", e.getMessage());
			return results;
		}
	}
}
