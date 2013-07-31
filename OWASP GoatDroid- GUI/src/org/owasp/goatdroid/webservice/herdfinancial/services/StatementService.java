package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.StatementModel;

public interface StatementService {

	public StatementModel getStatement(String accountNumber, String startDate,
			String endDate, String authToken);

	public StatementModel getStatementSinceLastPoll(String accountNumber,
			String authToken);
}
