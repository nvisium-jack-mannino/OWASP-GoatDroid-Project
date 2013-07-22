package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.TransferBean;

public interface TransferService {

	public TransferBean transferFunds(int sessionToken, String from, String to,
			double amount);
}
