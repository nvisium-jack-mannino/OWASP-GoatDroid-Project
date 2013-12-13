package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;

public interface TransferService {

	public BaseModel transferFunds(String authToken, String from, String to,
			double amount);
}
