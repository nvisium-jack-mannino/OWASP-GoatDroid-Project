package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;
import java.util.HashMap;

public interface BalanceDao {

	public HashMap<String, Double> getBalances(String accountNumber)
			throws SQLException;
}
