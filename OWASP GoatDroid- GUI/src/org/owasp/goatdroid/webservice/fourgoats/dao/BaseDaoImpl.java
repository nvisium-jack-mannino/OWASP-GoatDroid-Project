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

import javax.sql.DataSource;

import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class BaseDaoImpl extends JdbcDaoSupport implements BaseDao {

	public boolean checkSessionMatchesUserID(String sessionToken, String userID)
			throws SQLException {
		String sql = "select userID from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getUserID(String sessionToken) throws Exception {
		String sql = "select userID from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getString("userID");
	}

	public String getCheckinOwner(String checkinID) throws Exception {

		String sql = "select userID from checkins where checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		if (rs.next())
			return rs.getString("userID");
		else
			return "";
	}

	public boolean isCheckinOwnerProfilePublic(String checkinID)
			throws Exception {

		String sql = "select users.isPublic from checkins inner "
				+ "join users on checkins.userID = users.userID where checkins.checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		if (rs.next())
			return rs.getBoolean("isPublic");
		else
			return false;
	}

	public boolean isProfilePublic(String userID) throws Exception {

		String sql = "select isPublic from users where userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		if (rs.next())
			return rs.getBoolean("isPublic");
		else
			return false;
	}

	public boolean isFriend(String userID, String friendUserID)
			throws Exception {

		String sql = "select userID from friends where (userID = ? and friendUserID = ?) "
				+ " or (friendUserID = ? and userID = ?)";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, friendUserID, friendUserID, userID });
		if (rs.next())
			return true;
		else
			return false;
	}

	public boolean isAdmin(String userID) throws Exception {

		String sql = "select isAdmin from users where userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		if (rs.next())
			return rs.getBoolean("isAdmin");
		else
			return false;
	}

	public String getUserNameBySessionToken(String sessionToken)
			throws Exception {

		String sql = "select userName from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getString("userName");
	}

	public long getSessionStartTime(String sessionToken) throws Exception {

		String sql = "select sessionStartTime from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getLong("sessionStartTime");
	}

	public boolean isSessionValid(String sessionToken) throws Exception {

		String sql = "select sessionStartTime from users where sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		if (rs.next()) {
			Long sessionStartTime = rs.getLong("sessionStartTime");
			if (LoginUtils.getTimeMilliseconds() - sessionStartTime < Constants.SESSION_LIFETIME)
				return true;
			else
				return false;
		} else
			return false;
	}
}
