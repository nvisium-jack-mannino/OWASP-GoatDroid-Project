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
package org.owasp.goatdroid.herdfinancial.rest.transfer;

import java.util.HashMap;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.requestresponse.AuthenticatedRestClient;
import org.owasp.goatdroid.herdfinancial.requestresponse.RequestMethod;
import android.content.Context;

public class TransferRequest {

	Context context;
	String destinationInfo;

	public TransferRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public HashMap<String, String> doTransfer(String sessionToken,
			String fromAccount, String toAccount, String amount)
			throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/herdfinancial/api/v1/priv/transfer");
		client.AddParam("from", fromAccount);
		client.AddParam("to", toAccount);
		client.AddParam("amount", amount);
		client.Execute(RequestMethod.POST, context);
		return TransferResponse.parseStatusAndErrors(client.getResponse());
	}
}
