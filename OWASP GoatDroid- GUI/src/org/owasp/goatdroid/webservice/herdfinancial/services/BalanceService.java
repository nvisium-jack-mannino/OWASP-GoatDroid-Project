package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.BalanceBean;

public interface BalanceService {

	public BalanceBean getBalances(String accountNumber, int sessionToken);
}
