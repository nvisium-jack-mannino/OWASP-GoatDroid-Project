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

public class LoginDAO extends BaseDAO {

	public LoginDAO() {
		super();
	}

	public boolean isDevicePermanentlyAuthorized(String deviceID)
			throws SQLException {

		String sql = "select isDeviceAuthorized from users where deviceID= ?";
		PreparedStatement selectDeviceAuth = (PreparedStatement) conn
				.prepareCall(sql);
		selectDeviceAuth.setString(1, deviceID);
		ResultSet rs = selectDeviceAuth.executeQuery();
		if (rs.next()) {
			boolean isAuth = rs.getBoolean("isDeviceAuthorized");
			return isAuth;
		} else
			return false;
	}

	public void updateSession(String userName, int sessionToken,
			long sessionStartTime) throws SQLException {

		String sql = "update users set sessionToken = ?, sessionStartTime = ? where username = ?";
		PreparedStatement updateSessionToken = (PreparedStatement) conn
				.prepareCall(sql);
		updateSessionToken.setInt(1, sessionToken);
		updateSessionToken.setDouble(2, sessionStartTime);
		updateSessionToken.setString(3, userName);
		updateSessionToken.executeUpdate();
	}

	public long getSessionStartTime(int sessionToken) throws SQLException {

		String sql = "select sessionStartTime from users where sessionToken = ?";
		PreparedStatement selectSessionStart = (PreparedStatement) conn
				.prepareCall(sql);
		selectSessionStart.setInt(1, sessionToken);
		ResultSet rs = selectSessionStart.executeQuery();
		if (rs.next()) {
			long sessionStartTime = rs.getLong("sessionStartTime");
			return sessionStartTime;
		} else {
			return 0;
		}
	}

	public boolean validateCredentials(String userName, String password)
			throws SQLException {

		String sql = "select accountNumber from users where username = ? and password = ?";
		PreparedStatement selectUser = (PreparedStatement) conn
				.prepareCall(sql);
		selectUser.setString(1, userName);
		selectUser.setString(2, password);
		ResultSet rs = selectUser.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateAuthorizedDeviceSession(String deviceID,
			int sessionToken, long sessionStartTime) throws SQLException {

		String sql = "update users SET sessionToken = ?, sessionStartTime = ? where deviceID = ?";
		PreparedStatement updateDeviceSession = (PreparedStatement) conn
				.prepareCall(sql);
		updateDeviceSession.setInt(1, sessionToken);
		updateDeviceSession.setLong(2, sessionStartTime);
		updateDeviceSession.setString(3, deviceID);
		updateDeviceSession.executeUpdate();
	}

	public String getUserName(int sessionToken) throws SQLException {

		String sql = "select userName from users where sessionToken = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setInt(1, sessionToken);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("userName");
	}

	public String getAccountNumber(int sessionToken) throws SQLException {

		String sql = "select accountNumber from users where sessionToken = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setInt(1, sessionToken);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("accountNumber");
	}

	public void terminateSession(int sessionToken) throws SQLException {

		String sql = "update users SET sessionToken = 0, sessionStartTime = 0 where sessionToken = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setInt(1, sessionToken);
		updateStatement.executeUpdate();
	}
}
