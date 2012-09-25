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
import org.owasp.goatdroid.webservice.herdfinancial.Utils;

public class TransferDAO extends BaseDAO {

	public TransferDAO() {
		super();
	}

	public double getBalance(String accountNumber) throws SQLException {

		String sql = "select checkingBalance from users where accountNumber = ?";
		PreparedStatement selectBalance = (PreparedStatement) conn
				.prepareCall(sql);
		selectBalance.setString(1, accountNumber);
		ResultSet rs = selectBalance.executeQuery();
		double balance = 0;
		while (rs.next()) {
			balance = rs.getDouble("checkingBalance");
		}
		return balance;
	}

	public void insertTransaction(String accountNumber, Date date,
			double amount, String name, double balance) throws SQLException {
		String sql = "insert into transactions (accountNumber, date, amount, name, balance, timeStamp)"
				+ " values (?,?,?,?,?,?)";
		PreparedStatement insertStatement = (PreparedStatement) conn
				.prepareCall(sql);
		insertStatement.setString(1, accountNumber);
		insertStatement.setDate(2, date);
		insertStatement.setDouble(3, amount);
		insertStatement.setString(4, name);
		insertStatement.setDouble(5, balance + amount);
		insertStatement.setLong(6, Utils.getTimeMilliseconds());
		insertStatement.executeUpdate();
	}

	public void updateAccountBalance(String accountNumber, double amount,
			double balance) throws SQLException {
		String sql = "update users SET checkingBalance = ? where accountNumber = ?";
		PreparedStatement updateAccount = (PreparedStatement) conn
				.prepareCall(sql);
		updateAccount.setDouble(1, balance + amount);
		updateAccount.setString(2, accountNumber);
		updateAccount.executeUpdate();
	}

	public String getName(String accountNumber) throws SQLException {
		String sql = "select firstName, lastName from users where accountNumber = ?";
		PreparedStatement selectName = (PreparedStatement) conn
				.prepareCall(sql);
		selectName.setString(1, accountNumber);
		ResultSet rs = selectName.executeQuery();
		String name = "";
		while (rs.next()) {
			name += rs.getString("firstName") + " ";
			name += rs.getString("lastName");
		}
		return name;
	}

	public boolean doesAccountExist(String accountNumber) throws SQLException {

		String sql = "select * from users where accountNumber = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, accountNumber);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}
}
