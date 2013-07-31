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
package org.owasp.goatdroid.herdfinancial.rest.forgotpassword;

import java.util.HashMap;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.requestresponse.RequestMethod;
import org.owasp.goatdroid.herdfinancial.requestresponse.RestClient;
import android.content.Context;

public class ForgotPasswordRequest {

	Context context;
	String destinationInfo;

	public ForgotPasswordRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public HashMap<String, String> requestCode(String userName,
			String secretQuestionIndex, String secretQuestionAnswer)
			throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/herdfinancial/api/v1/priv/forgot-password/request-code");
		client.AddParam("userName", userName);
		client.AddParam("secretQuestionIndex", secretQuestionIndex);
		client.AddParam("secretQuestionAnswer", secretQuestionAnswer);
		client.Execute(RequestMethod.POST, context);
		return ForgotPasswordResponse
				.parseStatusAndErrors(client.getResponse());
	}

	public HashMap<String, String> verifyCode(String userName,
			String passwordResetCode) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/herdfinancial/api/v1/priv/forgot-password/verify-code");
		client.AddParam("userName", userName);
		client.AddParam("passwordResetCode", passwordResetCode);
		client.Execute(RequestMethod.POST, context);
		return ForgotPasswordResponse
				.parseStatusAndErrors(client.getResponse());
	}

	public HashMap<String, String> updatePassword(String userName,
			String passwordResetCode, String password) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/herdfinancial/api/v1/priv/forgot-password/update_password");
		client.AddParam("userName", userName);
		client.AddParam("passwordResetCode", passwordResetCode);
		client.AddParam("password", password);
		client.Execute(RequestMethod.POST, context);
		return ForgotPasswordResponse
				.parseStatusAndErrors(client.getResponse());
	}
}
