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
import org.owasp.goatdroid.herdfinancial.http.RestClient;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.response.LoginResponse;

import android.content.Context;

public class LoginRequest {

	Context context;
	String destinationInfo;

	public LoginRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public HashMap<String, String> isDeviceAuthorizedOrSessionValid(
			String deviceID) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/herdfinancial/api/v1/pub/login/device-or-session/"
				+ deviceID);
		client.Execute(RequestMethod.GET, context);
		return LoginResponse.parseStatusAndToken(client.getResponse());
	}

	public HashMap<String, String> validateCredentials(String userName,
			String password) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/herdfinancial/api/v1/pub/login/authenticate");
		client.AddParam("userName", userName);
		client.AddParam("password", password);
		client.Execute(RequestMethod.POST, context);

		return LoginResponse.parseStatusAndToken(client.getResponse());
	}

	public HashMap<String, String> logOut() throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/herdfinancial/api/v1/priv/user/sign-out");
		client.Execute(RequestMethod.GET, context);
		return LoginResponse.parseStatusAndErrors(client.getResponse());
	}
}
