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

import java.util.HashMap;
import javax.sql.DataSource;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGLoginDaoImpl extends BaseDaoImpl implements LoginDao {

	@Autowired
	public FGLoginDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean validateCredentials(String userName, String password) {

		String sql = "select username from users where username = ? and password = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(
				sql,
				new Object[] {
						userName,
						LoginUtils.generateSaltedSHA512Hash(password,
								Salts.PASSWORD_HASH_SALT) });
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateSessionInformation(String userName, String sessionToken,
			long sessionStartTime) {

		String sql = "update users SET sessionToken = ?, sessionStartTime = ? where userName = ?";
		getJdbcTemplate().update(sql,
				new Object[] { sessionToken, sessionStartTime, userName });
	}

	public HashMap<String, Boolean> getPreferences(String userName) {

		String sql = "select autoCheckin, isPublic, isAdmin from users "
				+ "where userName = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userName);
		HashMap<String, Boolean> preferences = new HashMap<String, Boolean>();
		rs.next();
		preferences.put("autoCheckin", rs.getBoolean("autoCheckin"));
		preferences.put("isPublic", rs.getBoolean("isPublic"));

		return preferences;
	}

	public void terminateSession(String sessionToken) {

		String sql = "update users SET sessionToken = '0', sessionStartTime = 0 where sessionToken = ?";
		getJdbcTemplate().update(sql, sessionToken);
	}

	public String getSessionToken(String userName) {
		String sql = "select sessionToken from users where username = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userName);
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
