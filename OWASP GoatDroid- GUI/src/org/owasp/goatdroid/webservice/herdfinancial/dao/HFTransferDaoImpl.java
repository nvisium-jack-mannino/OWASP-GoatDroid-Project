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

import javax.sql.DataSource;

import org.owasp.goatdroid.webservice.herdfinancial.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class HFTransferDaoImpl extends BaseDaoImpl implements TransferDao {

	@Autowired
	public HFTransferDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public double getBalance(String accountNumber) throws SQLException {

		String sql = "SELECT checkingBalance FROM app.hf_users WHERE accountNumber = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, accountNumber);
		double balance = 0;
		while (rs.next()) {
			balance = rs.getDouble("checkingBalance");
		}
		return balance;
	}

	public void insertTransaction(String accountNumber, Date date,
			double amount, String name, double balance) throws SQLException {
		String sql = "INSERT INTO app.hf_transactions (accountNumber, date, amount, name, balance, timeStamp)"
				+ " VALUES (?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { accountNumber, date, amount, name,
						balance + amount, Utils.getTimeMilliseconds() });
	}

	public void updateAccountBalance(String accountNumber, double amount,
			double balance) throws SQLException {
		String sql = "UPDATE app.hf_users SET checkingBalance = ? WHERE accountNumber = ?";
		getJdbcTemplate().update(sql,
				new Object[] { balance + amount, accountNumber });
	}

	public String getName(String accountNumber) throws SQLException {
		String sql = "SELECT firstName, lastName FROM app.hf_users WHERE accountNumber = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, accountNumber);
		String name = "";
		while (rs.next()) {
			name += rs.getString("firstName") + " ";
			name += rs.getString("lastName");
		}
		return name;
	}

	public boolean doesAccountExist(String accountNumber) throws SQLException {

		String sql = "SELECT * FROM app.hf_users WHERE accountNumber = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, accountNumber);
		if (rs.next())
			return true;
		else
			return false;
	}
}
