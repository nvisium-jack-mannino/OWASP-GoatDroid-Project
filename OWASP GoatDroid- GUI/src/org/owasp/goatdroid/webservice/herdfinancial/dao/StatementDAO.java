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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.owasp.goatdroid.webservice.herdfinancial.Utils;
import org.owasp.goatdroid.webservice.herdfinancial.model.StatementModel;

public class StatementDAO extends BaseDAO {

	public StatementDAO() {
		super();
	}

	public ArrayList<StatementModel> getStatement(String accountNumber,
			Date startDate, Date endDate) throws SQLException {

		ArrayList<StatementModel> transactions = new ArrayList<StatementModel>();
		String sql = "select date, amount, name, balance from "
				+ "transactions where accountNumber = ? and date >= ? "
				+ "and date <= ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, accountNumber);
		selectStatement.setDate(2, startDate);
		selectStatement.setDate(3, endDate);
		ResultSet rs = selectStatement.executeQuery();

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
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, accountNumber);
		selectStatement.setLong(2, timeStamp);
		ResultSet rs = selectStatement.executeQuery();

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
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, accountNumber);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return rs.getLong("lastPollTime");
		else
			return 0;
	}

	public void updateLastPollTime(String accountNumber) throws SQLException {
		String sql = "update users SET lastPollTime = ? where accountNumber = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setLong(1, Utils.getTimeMilliseconds());
		updateStatement.setString(2, accountNumber);
		updateStatement.executeUpdate();
	}
}
