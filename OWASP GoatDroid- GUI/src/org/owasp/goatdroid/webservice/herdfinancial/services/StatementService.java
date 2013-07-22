package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.StatementBean;

public interface StatementService {

	public StatementBean getStatement(String accountNumber, String startDate,
			String endDate, int sessionToken);

	public StatementBean getStatementSinceLastPoll(String accountNumber,
			int sessionToken);
}
