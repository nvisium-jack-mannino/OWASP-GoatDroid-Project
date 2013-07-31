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

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class HFLoginDaoImpl extends BaseDaoImpl implements LoginDao {

	@Autowired
	public HFLoginDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean isDevicePermanentlyAuthorized(String deviceID)
			throws SQLException {

		String sql = "SELECT isDeviceAuthorized FROM app.hf_users WHERE deviceID= ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, deviceID);
		if (rs.next()) {
			boolean isAuth = rs.getBoolean("isDeviceAuthorized");
			return isAuth;
		} else
			return false;
	}

	public void updateAuth(String userName, String authToken)
			throws SQLException {

		String sql = "UPDATE app.hf_users SET authToken = ? WHERE username = ?";
		getJdbcTemplate().update(sql, new Object[] { authToken, userName });
	}

	public boolean validateCredentials(String userName, String password)
			throws SQLException {

		String sql = "SELECT accountNumber FROM app.hf_users WHERE username = ? AND password = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userName, password });
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateAuthorizedDeviceAuth(String deviceID, String authToken)
			throws SQLException {

		String sql = "UPDATE app.hf_users SET authToken = ? WHERE deviceID = ?";
		getJdbcTemplate().update(sql, new Object[] { authToken, deviceID });
	}

	public String getUserName(String authToken) throws SQLException {

		String sql = "SELECT userName FROM app.hf_users WHERE authToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, authToken);
		rs.next();
		return rs.getString("userName");
	}

	public String getAccountNumber(String authToken) throws SQLException {

		String sql = "SELECT accountNumber FROM app.hf_users WHERE authToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, authToken);
		rs.next();
		return rs.getString("accountNumber");
	}
}
