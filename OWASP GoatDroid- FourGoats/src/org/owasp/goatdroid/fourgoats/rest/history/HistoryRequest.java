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
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.requestresponse.AuthenticatedRestClient;
import org.owasp.goatdroid.fourgoats.requestresponse.RequestMethod;
import android.content.Context;

public class HistoryRequest {

	Context context;
	String destinationInfo;

	public HistoryRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public ArrayList<HashMap<String, String>> getHistory(String sessionToken)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/history/list",
				sessionToken);
		client.Execute(RequestMethod.GET, context);

		return HistoryResponse.parseHistoryResponse(client.getResponse());
	}

	public ArrayList<HashMap<String, String>> getUserHistory(
			String sessionToken, String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/history/get_user_history/" + userName,
				sessionToken);
		client.Execute(RequestMethod.GET, context);

		return HistoryResponse.parseHistoryResponse(client.getResponse());
	}
}
