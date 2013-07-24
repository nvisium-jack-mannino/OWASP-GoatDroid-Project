/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.owasp.goatdroid.webservice.herdfinancial.Utils;
import org.owasp.goatdroid.webservice.herdfinancial.model.StatementModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class HFStatementDaoImpl extends BaseDaoImpl implements StatementDao {

	@Autowired
	public HFStatementDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ArrayList<StatementModel> getStatement(String accountNumber,
			Date startDate, Date endDate) throws SQLException {

		ArrayList<StatementModel> transactions = new ArrayList<StatementModel>();
		String sql = "select date, amount, name, balance from "
				+ "transactions where accountNumber = ? and date >= ? "
				+ "and date <= ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { accountNumber, startDate, endDate });
		while (rs.next()) {
			StatementModel transaction = new StatementModel();
			transaction.setDate(rs.getDate("date").toString());
			transaction.setAmount(Double.toString(rs.getDouble("amount")));
			transaction.setName(rs.getString("name"));
			transaction.setBalance(Double.toString(rs.getDouble("balance")));
			// add the current transaction
			transactions.add(transaction);
		}
		return transactions;
	}

	public ArrayList<StatementModel> getTransactionsSinceLastPoll(
			String accountNumber, long timeStamp) throws SQLException {
		ArrayList<StatementModel> transactions = new ArrayList<StatementModel>();
		String sql = "select date, amount, name, balance from transactions where accountNumber = ? and timeStamp >  ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { accountNumber, timeStamp });
		while (rs.next()) {
			StatementModel transaction = new StatementModel();
			transaction.setDate(rs.getDate("date").toString());
			transaction.setAmount(Double.toString(rs.getDouble("amount")));
			transaction.setName(rs.getString("name"));
			transaction.setBalance(Double.toString(rs.getDouble("balance")));
			// add the current transaction
			transactions.add(transaction);
		}
		return transactions;
	}

	public long getLastPollTime(String accountNumber) throws SQLException {
		String sql = "select lastPollTime from users where accountNumber = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, accountNumber);
		if (rs.next())
			return rs.getLong("lastPollTime");
		else
			return 0;
	}

	public void updateLastPollTime(String accountNumber) throws SQLException {
		String sql = "update users SET lastPollTime = ? where accountNumber = ?";
		getJdbcTemplate().update(sql,
				new Object[] { Utils.getTimeMilliseconds(), accountNumber });
	}
}
