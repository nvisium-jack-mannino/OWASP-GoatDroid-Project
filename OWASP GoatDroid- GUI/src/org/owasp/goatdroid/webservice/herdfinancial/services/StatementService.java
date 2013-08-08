package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.Statement;

public interface StatementService {

	public Statement getStatement(String accountNumber, String startDate,
			String endDate, String authToken);

	public Statement getStatementSinceLastPoll(String accountNumber,
			String authToken);
}
