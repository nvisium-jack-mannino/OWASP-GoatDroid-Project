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

import java.sql.SQLException;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterDaoImpl extends BaseDaoImpl implements RegisterDao {

	public void registerUser(String accountNumber, String firstName,
			String lastName, String userName, String password)
			throws SQLException {
		String sql = "insert into users (accountNumber,"
				+ "firstName, lastName, deviceID, isDeviceAuthorized, checkingBalance, savingsBalance,"
				+ "username, password, sessionToken, sessionStartTime) values (?,?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { accountNumber, firstName, lastName, "", false,
						0, 0, userName, password, 0, 0 });
	}

	public boolean doesUserNameExist(String userName) throws SQLException {
		String sql = "select userName from users where userName = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userName);
		if (rs.next())
			return true;
		else
			return false;
	}

	public boolean doesAccountNumberExist(String accountNumber)
			throws SQLException {
		String sql = "select accountNumber from users where accountNumber = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, accountNumber);
		if (rs.next())
			return true;
		else
			return false;
	}
}
