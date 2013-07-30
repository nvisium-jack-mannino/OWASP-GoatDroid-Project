package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.TransferModel;

public interface TransferService {

	public TransferModel transferFunds(int sessionToken, String from, String to,
			double amount);
}
