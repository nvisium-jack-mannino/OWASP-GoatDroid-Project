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
package org.owasp.goatdroid.fourgoats.requestresponse;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseBase {

	static public HashMap<String, String> getSuccessAndErrors(String response) {

		JSONObject json;
		HashMap<String, String> results = new HashMap<String, String>();

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("false")) {
				results.put("success", "false");
				return results;
			} else {
				results.put("success", "true");
				return results;
			}

		} catch (JSONException e) {
			results.put("success", "false");
			return results;
		}
	}
}
