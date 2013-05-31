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
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.requestresponse.AuthenticatedRestClient;
import org.owasp.goatdroid.fourgoats.requestresponse.RequestMethod;
import org.owasp.goatdroid.fourgoats.requestresponse.RestClient;
import android.content.Context;

public class LoginRequest {

	Context context;
	String destinationInfo;

	public LoginRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public boolean isSessionValid(String sessionToken) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/fourgoats/api/v1/login/check_session");
		client.AddHeader("Cookie", "SESS=" + sessionToken);
		client.Execute(RequestMethod.GET, context);
		return LoginResponse.isSuccess(client.getResponse());
	}

	public HashMap<String, String> validateCredentials(String userName,
			String password) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/fourgoats/api/v1/login/authenticate");
		client.AddParam("userName", userName);
		client.AddParam("password", password);
		client.Execute(RequestMethod.POST, context);

		return LoginResponse.parseLoginResponse(client.getResponse());
	}

	public HashMap<String, String> validateCredentialsAPI(String userName,
			String password) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/fourgoats/api/v1/login/validate_api");
		client.AddParam("userName", userName);
		client.AddParam("password", password);
		client.Execute(RequestMethod.POST, context);

		return LoginResponse.parseAPILoginResponse(client.getResponse());
	}

	public HashMap<String, String> logOut(String sessionToken) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/login/sign_out",
				sessionToken);
		client.Execute(RequestMethod.POST, context);

		return LoginResponse.parseStatusAndErrors(client.getResponse());
	}
}
