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
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGCommentDaoImpl extends BaseDaoImpl implements CommentDao {

	@Autowired
	public FGCommentDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void insertComment(String dateTime, String commentID, String userID,
			String comment, String checkinID) throws SQLException {

		String sql = "INSERT INTO app.fg_comments (dateTime, commentID, userID, comment, checkinID) VALUES "
				+ "(?,?,?,?,?)";
		getJdbcTemplate()
				.update(sql,
						new Object[] { dateTime, commentID, userID, comment,
								checkinID });
	}

	public void deleteComment(String commentID) throws SQLException {

		String sql = "DELETE FROM app.fg_comments WHERE commentID = ?";
		getJdbcTemplate().update(sql, commentID);
	}

	public HashMap<String, String> selectComments(String checkinID)
			throws SQLException {

		String sql = "SELECT app.fg_comments.dateTime, app.fg_comments.commentID, app.fg_comments.userID, app.fg_users.firstName, "
				+ "app.fg_users.lastName, app.fg_comments.comment FROM app.fg_comments INNER JOIN app.fg_users ON "
				+ "app.fg_comments.userID = app.fg_users.userID WHERE app.fg_comments.checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		HashMap<String, String> comments = new HashMap<String, String>();
		int count = 0;
		while (rs.next()) {
			comments.put("dateTime" + count, rs.getString("dateTime"));
			comments.put("commentID" + count, rs.getString("commentID"));
			comments.put("userID" + count, rs.getString("userID"));
			comments.put("firstName" + count, rs.getString("firstName"));
			comments.put("lastName" + count, rs.getString("lastName"));
			comments.put("comment" + count, rs.getString("comment"));
			count++;
		}
		return comments;
	}

	public boolean isCommentOwner(String userID, String commentID)
			throws SQLException {

		String sql = "SELECT userID FROM app.fg_comments WHERE userID = ? AND commentID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, commentID });
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getCheckinOwner(String checkinID) throws SQLException {

		String sql = "SELECT userID FROM app.fg_checkins WHERE checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		rs.next();
		return rs.getString("userID");
	}

	public String getCheckinID(String commentID) throws SQLException {

		String sql = "SELECT checkinID FROM app.fg_comments WHERE commentID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, commentID);
		rs.next();
		return rs.getString("checkinID");
	}

	public HashMap<String, String> getVenueInfo(String checkinID)
			throws SQLException {

		String sql = "SELECT app.fg_venues.venueName, app.fg_venues.venueWebsite FROM app.fg_venues INNER "
				+ "JOIN app.fg_checkins ON app.fg_venues.venueID = app.fg_checkins.venueID WHERE app.fg_checkins.checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		HashMap<String, String> venueInfo = new HashMap<String, String>();
		while (rs.next()) {
			venueInfo.put("venueName", rs.getString("venueName"));
			venueInfo.put("venueWebsite", rs.getString("venueWebsite"));
		}
		return venueInfo;
	}
}
