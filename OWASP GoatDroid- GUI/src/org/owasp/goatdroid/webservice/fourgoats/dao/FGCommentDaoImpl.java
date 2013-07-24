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

		String sql = "insert into comments (dateTime, commentID, userID, comment, checkinID) values "
				+ "(?,?,?,?,?)";
		getJdbcTemplate()
				.update(sql,
						new Object[] { dateTime, commentID, userID, comment,
								checkinID });
	}

	public void deleteComment(String commentID) throws SQLException {

		String sql = "delete from comments where commentID = ?";
		getJdbcTemplate().update(sql, commentID);
	}

	public HashMap<String, String> selectComments(String checkinID)
			throws SQLException {

		String sql = "select comments.dateTime, comments.commentID, comments.userID, users.firstName, "
				+ "users.lastName, comments.comment from comments inner join users on "
				+ "comments.userID = users.userID where comments.checkinID = ?";
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

		String sql = "select userID from comments where userID = ? and commentID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, commentID });
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getCheckinOwner(String checkinID) throws SQLException {

		String sql = "select userID from checkins where checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		rs.next();
		return rs.getString("userID");
	}

	public String getCheckinID(String commentID) throws SQLException {

		String sql = "select checkinID from comments where commentID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, commentID);
		rs.next();
		return rs.getString("checkinID");
	}

	public HashMap<String, String> getVenueInfo(String checkinID)
			throws SQLException {

		String sql = "select venues.venueName, venues.venueWebsite from venues inner "
				+ "join checkins on venues.venueID = checkins.venueID where checkins.checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);

		HashMap<String, String> venueInfo = new HashMap<String, String>();

		while (rs.next()) {

			venueInfo.put("venueName", rs.getString("venueName"));
			venueInfo.put("venueWebsite", rs.getString("venueWebsite"));
		}

		return venueInfo;
	}
}
