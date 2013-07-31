package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.BalanceModel;

public interface BalanceService {

	public BalanceModel getBalances(String accountNumber, String authToken);
}
