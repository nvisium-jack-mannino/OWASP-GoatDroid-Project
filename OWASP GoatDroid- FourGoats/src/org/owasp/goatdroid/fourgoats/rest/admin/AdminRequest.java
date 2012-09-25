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
package org.owasp.goatdroid.fourgoats.rest.admin;

import java.util.ArrayList;
import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.requestresponse.AuthenticatedRestClient;
import org.owasp.goatdroid.fourgoats.requestresponse.RequestMethod;
import android.content.Context;

public class AdminRequest {

	String destinationInfo;
	Context context;

	public AdminRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public HashMap<String, String> deleteUser(String sessionToken,
			String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/admin/delete_user",
				sessionToken);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return AdminResponse.parseStatusAndErrors(client.getResponse());
	}

	public HashMap<String, String> resetUserPassword(String sessionToken,
			String userName, String newPassword) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/admin/reset_password",
				sessionToken);
		client.AddParam("userName", userName);
		client.AddParam("newPassword", newPassword);
		client.Execute(RequestMethod.POST, context);

		return AdminResponse.parseStatusAndErrors(client.getResponse());
	}

	public ArrayList<HashMap<String, String>> getUsers(String sessionToken)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/admin/get_users",
				sessionToken);
		client.Execute(RequestMethod.GET, context);

		return AdminResponse.parseGetUsersResponse(client.getResponse());
	}

}
