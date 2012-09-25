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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAO extends BaseDAO {

	public RegisterDAO() {
		super();
	}

	public void registerUser(String accountNumber, String firstName,
			String lastName, String userName, String password)
			throws SQLException {
		String sql = "insert into users (accountNumber,"
				+ "firstName, lastName, deviceID, isDeviceAuthorized, checkingBalance, savingsBalance,"
				+ "username, password, sessionToken, sessionStartTime) values (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertUser = (PreparedStatement) conn
				.prepareCall(sql);
		insertUser.setString(1, accountNumber);
		insertUser.setString(2, firstName);
		insertUser.setString(3, lastName);
		insertUser.setString(4, ""); // deviceID
		insertUser.setBoolean(5, false); // device auth
		insertUser.setInt(6, 0); // checking balance
		insertUser.setInt(7, 0); // savings balance
		insertUser.setString(8, userName);
		insertUser.setString(9, password);
		insertUser.setInt(10, 0); // sessionToken
		insertUser.setInt(11, 0); // sessionStartTime
		insertUser.executeUpdate();
	}

	public boolean doesUserNameExist(String userName) throws SQLException {
		String sql = "select userName from users where userName = ?";
		PreparedStatement selectUserName = (PreparedStatement) conn
				.prepareCall(sql);
		selectUserName.setString(1, userName);
		ResultSet rs = selectUserName.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public boolean doesAccountNumberExist(String accountNumber)
			throws SQLException {
		String sql = "select accountNumber from users where accountNumber = ?";
		PreparedStatement selectAccountNumber = (PreparedStatement) conn
				.prepareCall(sql);
		selectAccountNumber.setString(1, accountNumber);
		ResultSet rs = selectAccountNumber.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}
}
