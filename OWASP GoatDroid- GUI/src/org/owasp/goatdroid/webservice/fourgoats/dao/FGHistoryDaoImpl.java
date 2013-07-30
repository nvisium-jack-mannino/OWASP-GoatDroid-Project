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
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.owasp.goatdroid.webservice.fourgoats.model.HistoryEventModel;
import org.owasp.goatdroid.webservice.fourgoats.model.HistoryEventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGHistoryDaoImpl extends BaseDaoImpl implements HistoryDao {

	@Autowired
	public FGHistoryDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ArrayList<HistoryEventModel> getCheckinHistory(String userID)
			throws SQLException {

		String sql = "SELECT app.fg_checkins.dateTime, app.fg_checkins.checkinID, app.fg_checkins.latitude, app.fg_checkins.longitude, "
				+ "app.fg_venues.venueName, app.fg_venues.venueWebsite FROM app.fg_checkins INNER JOIN app.fg_venues on "
				+ "app.fg_checkins.venueID = app.fg_venues.venueID where app.fg_checkins.userID = ?";
		SqlRowSet rs;
		ArrayList<HistoryEventModel> checkins = new ArrayList<HistoryEventModel>();
		try {
			rs = getJdbcTemplate().queryForRowSet(sql, userID);
			while (rs.next()) {
				HistoryEventModel checkin = new HistoryEventModel();
				checkin.setDateTime(rs.getString("dateTime"));
				checkin.setCheckinID(rs.getString("checkinID"));
				checkin.setLatitude(rs.getString("latitude"));
				checkin.setLongitude(rs.getString("longitude"));
				checkin.setVenueName(rs.getString("venueName"));
				checkin.setVenueWebsite(rs.getString("venueWebsite"));
				checkins.add(checkin);
			}
		} catch (DataAccessException e) {
			e.getMessage();
		}

		return checkins;
	}

	public ArrayList<HistoryEventModel> getCheckinHistoryByUserName(
			String userName) throws SQLException {

		String sql = "SELECT app.fg_checkins.dateTime, app.fg_checkins.checkinID, app.fg_checkins.latitude, app.fg_checkins.longitude, "
				+ "app.fg_venues.venueName, app.fg_venues.venueWebsite FROM app.fg_checkins INNER JOIN app.fg_venues ON "
				+ "app.fg_checkins.venueID = app.fg_venues.venueID INNER JOIN app.fg_users ON app.fg_checkins.userID = app.fg_users.userID WHERE "
				+ "app.fg_users.userName = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userName);
		ArrayList<HistoryEventModel> checkins = new ArrayList<HistoryEventModel>();
		while (rs.next()) {
			HistoryEventModel checkin = new HistoryEventModel();
			checkin.setDateTime(rs.getString("dateTime"));
			checkin.setCheckinID(rs.getString("checkinID"));
			checkin.setLatitude(rs.getString("latitude"));
			checkin.setLongitude(rs.getString("longitude"));
			checkin.setVenueName(rs.getString("venueName"));
			checkin.setVenueWebsite(rs.getString("venueWebsite"));
			checkins.add(checkin);
		}
		return checkins;
	}

	public String getVenueName(String checkinID) throws SQLException {

		String sql = "SELECT app.fg_venues.venueName FROM app.fg_checkins INNER "
				+ "JOIN app.fg_venues ON app.fg_checkins.venueID = app.fg_venues.venueID WHERE checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		rs.next();
		return rs.getString("venueName");
	}

	public HashMap<String, String> getCheckinInfo(String checkinID)
			throws SQLException {

		String sql = "SELECT dateTime, latitude, longitude FROM app.fg_checkins WHERE checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		HashMap<String, String> checkin = new HashMap<String, String>();
		while (rs.next()) {
			checkin.put("dateTime", rs.getString("dateTime"));
			checkin.put("latitude", rs.getString("latitude"));
			checkin.put("longitude", rs.getString("longitude"));
		}
		return checkin;
	}

	public HashMap<String, String> selectComments(String checkinID)
			throws SQLException {

		String sql = "SELECT app.fg_comments.commentID, app.fg_comments.userID, app.fg_users.firstName, "
				+ "app.fg_users.lastName, app.fg_comments.comment FROM app.fg_comments INNER JOIN app.fg_users ON "
				+ "app.fg_comments.userID = app.fg_users.userID WHERE app.fg_comments.checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		HashMap<String, String> comments = new HashMap<String, String>();
		int count = 0;
		while (rs.next()) {
			comments.put("commentID" + count, rs.getString("commentID"));
			comments.put("userID" + count, rs.getString("userID"));
			comments.put("firstName" + count, rs.getString("firstName"));
			comments.put("lastName" + count, rs.getString("lastName"));
			comments.put("comment" + count, rs.getString("comment"));
			count++;
		}
		return comments;
	}

	public boolean isProfilePublic(String userID) throws SQLException {

		String sql = "SELECT isPublic FROM app.fg_users where userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		rs.next();
		return rs.getBoolean("isPublic");

	}

	public boolean isFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "SELECT userID FROM app.fg_friends WHERE (userID = ? AND friendUserID = ?) "
				+ " OR (friendUserID = ? AND userID = ?)";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, friendUserID, friendUserID, userID });
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getVenueWebsite(String checkinID) throws SQLException {

		String sql = "SELECT app.fg_venues.venueWebsite FROM app.fg_checkins INNER "
				+ "JOIN app.fg_venues ON app.fg_checkins.venueID = app.fg_venues.venueID WHERE checkinID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, checkinID);
		rs.next();
		return rs.getString("venueWebsite");
	}
}
