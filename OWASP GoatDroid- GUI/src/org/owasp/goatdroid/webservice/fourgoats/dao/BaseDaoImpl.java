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

import java.sql.SQLException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class BaseDaoImpl extends JdbcDaoSupport implements BaseDao {

	public boolean checkSessionMatchesUserID(String sessionToken, String userID)
			throws SQLException {
		String sql = "SELECT userID FROM app.fg_users WHERE sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getUserID(String sessionToken) throws Exception {
		String sql = "SELECT userID FROM app.fg_users WHERE sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getString("userID");
	}

	public String getCheckinOwner(String checkinID) throws Exception {

		String sql = "SELECT userID FROM app.fg_checkins WHERE checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		if (rs.next())
			return rs.getString("userID");
		else
			return "";
	}

	public boolean isCheckinOwnerProfilePublic(String checkinID)
			throws Exception {

		String sql = "SELECT app.fg_users.isPublic FROM app.fg_checkins INNER "
				+ "JOIN app.fg_users ON app.fg_checkins.userID = app.fg_users.userID "
				+ "WHERE app.fg_checkins.checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		if (rs.next())
			return rs.getBoolean("isPublic");
		else
			return false;
	}

	public boolean isProfilePublic(String userID) throws Exception {

		String sql = "SELECT isPublic FROM app.fg_users WHERE userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		if (rs.next())
			return rs.getBoolean("isPublic");
		else
			return false;
	}

	public boolean isFriend(String userID, String friendUserID)
			throws Exception {

		String sql = "SELECT userID FROM app.fg_friends WHERE (userID = ? AND friendUserID = ?) "
				+ " OR (friendUserID = ? AND userID = ?)";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, friendUserID, friendUserID, userID });
		if (rs.next())
			return true;
		else
			return false;
	}

	public boolean isAdmin(String userID) throws Exception {

		String sql = "SELECT isAdmin FROM app.fg_users WHERE userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		if (rs.next())
			return rs.getBoolean("isAdmin");
		else
			return false;
	}

	public String getUserNameBySessionToken(String sessionToken)
			throws Exception {

		String sql = "SELECT userName FROM app.fg_users WHERE sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getString("userName");
	}

	public boolean isAuthValid(String userName, String authToken)
			throws Exception {

		String sql = "SELECT * FROM app.fg_users WHERE userName = ? AND authToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userName, authToken });
		if (rs.next()) {
			return true;
		} else
			return false;
	}
}
