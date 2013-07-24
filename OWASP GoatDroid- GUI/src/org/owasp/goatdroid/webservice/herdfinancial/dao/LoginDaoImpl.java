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
public class LoginDaoImpl extends BaseDaoImpl implements LoginDao {

	public boolean isDevicePermanentlyAuthorized(String deviceID)
			throws SQLException {

		String sql = "select isDeviceAuthorized from users where deviceID= ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, deviceID);
		if (rs.next()) {
			boolean isAuth = rs.getBoolean("isDeviceAuthorized");
			return isAuth;
		} else
			return false;
	}

	public void updateSession(String userName, int sessionToken,
			long sessionStartTime) throws SQLException {

		String sql = "update users set sessionToken = ?, sessionStartTime = ? where username = ?";
		getJdbcTemplate().update(sql,
				new Object[] { sessionToken, sessionStartTime, userName });
	}

	public long getSessionStartTime(int sessionToken) throws SQLException {

		String sql = "select sessionStartTime from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
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
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userName, password });
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateAuthorizedDeviceSession(String deviceID,
			int sessionToken, long sessionStartTime) throws SQLException {

		String sql = "update users SET sessionToken = ?, sessionStartTime = ? where deviceID = ?";
		getJdbcTemplate().update(sql,
				new Object[] { sessionToken, sessionStartTime, deviceID });
	}

	public String getUserName(int sessionToken) throws SQLException {

		String sql = "select userName from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getString("userName");
	}

	public String getAccountNumber(int sessionToken) throws SQLException {

		String sql = "select accountNumber from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getString("accountNumber");
	}

	public void terminateSession(int sessionToken) throws SQLException {

		String sql = "update users SET sessionToken = 0, sessionStartTime = 0 where sessionToken = ?";
		getJdbcTemplate().update(sql, sessionToken);
	}
}
