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
package org.owasp.goatdroid.herdfinancial.rest.secretquestions;

import java.util.HashMap;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.requestresponse.AuthenticatedRestClient;
import org.owasp.goatdroid.herdfinancial.requestresponse.RequestMethod;
import android.content.Context;

public class SecretQuestionsRequest {

	Context context;
	String destinationInfo;

	public SecretQuestionsRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public HashMap<String, String> setSecretQuestions(String sessionToken,
			String answer1, String answer2, String answer3) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo
				+ "/herdfinancial/api/v1/priv/secret_questions/set",
				sessionToken);
		client.AddParam("answer1", answer1);
		client.AddParam("answer2", answer2);
		client.AddParam("answer3", answer3);
		client.Execute(RequestMethod.POST, context);
		return SecretQuestionsResponse.parseStatusAndErrors(client
				.getResponse());
	}
}
