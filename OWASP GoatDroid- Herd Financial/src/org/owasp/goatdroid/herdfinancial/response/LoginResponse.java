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
import org.owasp.goatdroid.herdfinancial.base.ResponseBase;

public class LoginResponse extends ResponseBase {

	static public HashMap<String, String> parseStatusAndToken(String response) {

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
				if (json.getString("sessionToken").equals("")
						|| json.getString("sessionToken").equals("0")) {
					results.put("sessionToken", "0");
					return results;
				} else {
					results.put("sessionToken", json.getString("sessionToken"));
					results.put("userName", json.getString("userName"));
					results.put("accountNumber",
							json.getString("accountNumber"));
					return results;
				}
			}

		} catch (JSONException e) {
			results.put("success", "false");
			results.put("errors", e.getMessage());
			return results;
		} catch (Exception e) {
			results.put("success", "false");
			results.put("errors", e.getMessage());
			return results;
		}
	}
}
