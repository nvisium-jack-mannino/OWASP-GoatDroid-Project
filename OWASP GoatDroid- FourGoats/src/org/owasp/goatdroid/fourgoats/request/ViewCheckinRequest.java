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
import org.owasp.goatdroid.fourgoats.response.ViewCheckinResponse;
import org.owasp.goatdroid.fourgoats.responseobjects.CheckinComments;
import android.content.Context;

public class ViewCheckinRequest {

	Context context;
	String destinationInfo;

	public ViewCheckinRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public CheckinComments getCheckin(String checkinID) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/priv/comments/get/"
				+ checkinID, context);
		// client.AddParam("checkinID", checkinID);
		client.Execute(RequestMethod.GET, context);

		return ViewCheckinResponse.parseCheckinResponse(client.getResponse());
	}
}
