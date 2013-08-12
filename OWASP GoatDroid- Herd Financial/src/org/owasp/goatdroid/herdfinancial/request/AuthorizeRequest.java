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
package org.owasp.goatdroid.herdfinancial.request;

import java.util.HashMap;

import org.owasp.goatdroid.herdfinancial.http.AuthenticatedRestClient;
import org.owasp.goatdroid.herdfinancial.http.RequestMethod;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.response.AuthorizeResponse;

import android.content.Context;

public class AuthorizeRequest {

	String destinationInfo;
	Context context;

	public AuthorizeRequest(Context context) {

		destinationInfo = Utils.getDestinationInfo(context);
		this.context = context;
	}

	public HashMap<String, String> authorizeDevice(String accountNumber)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/herdfinancial/api/v1/priv/authorize");
		client.AddParam("deviceID", Utils.getDeviceID(context));
		client.Execute(RequestMethod.POST, context);
		return AuthorizeResponse.parseStatusAndErrors(client.getResponse());
	}

}
