package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.StatementModel;

public interface StatementService {

	public StatementModel getStatement(String accountNumber, String startDate,
			String endDate, int sessionToken);

	public StatementModel getStatementSinceLastPoll(String accountNumber,
			int sessionToken);
}
