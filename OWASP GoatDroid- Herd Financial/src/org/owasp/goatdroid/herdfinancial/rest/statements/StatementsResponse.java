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
package org.owasp.goatdroid.herdfinancial.rest.statements;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.goatdroid.herdfinancial.base.ResponseBase;

public class StatementsResponse extends ResponseBase {

	static public ArrayList<HashMap<String, String>> parseStatusAndStatement(
			String response) {

		JSONObject json;
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();

		try {
			json = new JSONObject(response);
			if (json.getString("success").equals("true")) {
				JSONArray transactionArray = json.getJSONArray("statementData");
				for (int count = 0; count < transactionArray.length(); count++) {
					HashMap<String, String> transaction = new HashMap<String, String>();
					transaction.put("amount", (String) transactionArray
							.getJSONObject(count).get("amount"));
					transaction.put("balance", (String) transactionArray
							.getJSONObject(count).get("balance"));
					transaction.put("date", (String) transactionArray
							.getJSONObject(count).get("date"));
					transaction.put("name", (String) transactionArray
							.getJSONObject(count).get("name"));
					results.add(transaction);
				}
			}
		} catch (JSONException e) {
			try {
				json = new JSONObject(response);
				HashMap<String, String> transaction = new HashMap<String, String>();
				transaction.put(
						"amount",
						(String) json.getJSONObject("statementData").get(
								"amount"));
				transaction.put(
						"balance",
						(String) json.getJSONObject("statementData").get(
								"balance"));
				transaction.put("date",
						(String) json.getJSONObject("statementData")
								.get("date"));
				transaction.put("name",
						(String) json.getJSONObject("statementData")
								.get("name"));
				results.add(transaction);
			} catch (JSONException e1) {
				/*
				 * We don't care if it falls through here.
				 */
				e.getMessage();
			}
		}
		return results;
	}
}
