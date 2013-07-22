package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.owasp.goatdroid.webservice.herdfinancial.model.StatementModel;

public interface StatementDao {

	public ArrayList<StatementModel> getStatement(String accountNumber,
			Date startDate, Date endDate) throws SQLException;

	public ArrayList<StatementModel> getTransactionsSinceLastPoll(
			String accountNumber, long timeStamp) throws SQLException;

	public long getLastPollTime(String accountNumber) throws SQLException;

	public void updateLastPollTime(String accountNumber) throws SQLException;

}
