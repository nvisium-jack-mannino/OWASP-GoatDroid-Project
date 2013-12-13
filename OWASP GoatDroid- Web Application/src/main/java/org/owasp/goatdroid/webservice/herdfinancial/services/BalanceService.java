package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.Balance;

public interface BalanceService {

	public Balance getBalances(String accountNumber, String authToken);
}
