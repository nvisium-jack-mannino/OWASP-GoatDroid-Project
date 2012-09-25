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
package org.owasp.goatdroid.fourgoats.rest.register;

import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.requestresponse.RequestMethod;
import org.owasp.goatdroid.fourgoats.requestresponse.RestClient;
import android.content.Context;

public class RegisterRequest {

	Context context;
	String destinationInfo;

	public RegisterRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public HashMap<String, String> validateRegistration(String firstName,
			String lastName, String userName, String password) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/fourgoats/api/v1/register");
		client.AddParam("firstName", firstName);
		client.AddParam("lastName", lastName);
		client.AddParam("userName", userName);
		client.AddParam("password", password);
		client.Execute(RequestMethod.POST, context);

		return RegisterResponse.parseStatusAndErrors(client.getResponse());
	}
}
