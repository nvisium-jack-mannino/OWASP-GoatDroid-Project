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
package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;

public class LoginDAO extends BaseDAO {

	public LoginDAO() {
		super();
	}

	public boolean validateCredentials(String userName, String password)
			throws SQLException {

		String sql = "select username from users where username = ? and password = ?";
		PreparedStatement selectUser = (PreparedStatement) conn
				.prepareCall(sql);
		selectUser.setString(1, userName);
		selectUser.setString(2, LoginUtils.generateSaltedSHA512Hash(password,
				Salts.PASSWORD_HASH_SALT));
		ResultSet rs = selectUser.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateSessionInformation(String userName, String sessionToken,
			long sessionStartTime) throws SQLException {

		String sql = "update users SET sessionToken = ?, sessionStartTime = ? where userName = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, sessionToken);
		updateStatement.setDouble(2, sessionStartTime);
		updateStatement.setString(3, userName);
		updateStatement.executeUpdate();
	}

	public HashMap<String, Boolean> getPreferences(String userName)
			throws SQLException {

		String sql = "select autoCheckin, isPublic, isAdmin from users "
				+ "where userName = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userName);
		ResultSet rs = selectStatement.executeQuery();
		HashMap<String, Boolean> preferences = new HashMap<String, Boolean>();
		rs.next();
		preferences.put("autoCheckin", rs.getBoolean("autoCheckin"));
		preferences.put("isPublic", rs.getBoolean("isPublic"));
		preferences.put("isAdmin", rs.getBoolean("isAdmin"));
		return preferences;
	}

	public void terminateSession(String sessionToken) throws SQLException {

		String sql = "update users SET sessionToken = '0', sessionStartTime = 0 where sessionToken = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, sessionToken);
		updateStatement.executeUpdate();
	}

	public String getSessionToken(String userName) throws SQLException {
		String sql = "select sessionToken from users where username = ?";
		PreparedStatement selectUser = (PreparedStatement) conn
				.prepareCall(sql);
		selectUser.setString(1, userName);
		ResultSet rs = selectUser.executeQuery();
		if (rs.next()) {
			if (rs.getString("sessionToken") != null)
				return rs.getString("sessionToken");
			else
				return "";
		} else {
			return "";
		}
	}
}
