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
package org.owasp.goatdroid.fourgoats.request;

import org.owasp.goatdroid.fourgoats.http.AuthenticatedRestClient;
import org.owasp.goatdroid.fourgoats.http.RequestMethod;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.response.AdminResponse;
import org.owasp.goatdroid.fourgoats.responseobjects.Admin;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;
import org.owasp.goatdroid.fourgoats.responseobjects.ResponseObject;

import android.content.Context;

public class AdminRequest {

	String destinationInfo;
	Context context;

	public AdminRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public GenericResponseObject deleteUser(String userName) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/priv/admin/delete_user",
				context);
		client.AddParam("userName", userName);
		client.Execute(RequestMethod.POST, context);

		return AdminResponse.parseDeleteUserResponse(client.getResponse());
	}

	public GenericResponseObject resetUserPassword(String userName,
			String newPassword) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/fourgoats/api/v1/priv/admin/reset-password", context);
		client.AddParam("userName", userName);
		client.AddParam("newPassword", newPassword);
		client.Execute(RequestMethod.POST, context);

		return AdminResponse.parseResetUserPassword(client.getResponse());
	}

	public Admin getUsers() throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/priv/admin/get_users",
				context);
		client.Execute(RequestMethod.GET, context);

		return AdminResponse.parseGetUsersResponse(client.getResponse());
	}

}
