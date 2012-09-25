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
import java.util.ArrayList;
import java.util.HashMap;

import org.owasp.goatdroid.webservice.fourgoats.model.HistoryModel;

public class HistoryDAO extends BaseDAO {

	public HistoryDAO() {
		super();
	}

	public ArrayList<HistoryModel> getCheckinHistory(String userID)
			throws SQLException {

		String sql = "select checkins.dateTime, checkins.checkinID, checkins.latitude, checkins.longitude, "
				+ "venues.venueName, venues.venueWebsite from checkins inner join venues on "
				+ "checkins.venueID = venues.venueID where userID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		ResultSet rs = selectStatement.executeQuery();
		ArrayList<HistoryModel> checkins = new ArrayList<HistoryModel>();
		while (rs.next()) {
			HistoryModel checkin = new HistoryModel();
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

	public ArrayList<HistoryModel> getCheckinHistoryByUserName(String userName)
			throws SQLException {

		String sql = "select checkins.dateTime, checkins.checkinID, checkins.latitude, checkins.longitude, "
				+ "venues.venueName, venues.venueWebsite from checkins inner join venues on "
				+ "checkins.venueID = venues.venueID inner join users on checkins.userID = users.userID where users.userName = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userName);
		ResultSet rs = selectStatement.executeQuery();
		ArrayList<HistoryModel> checkins = new ArrayList<HistoryModel>();
		while (rs.next()) {
			HistoryModel checkin = new HistoryModel();
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

		String sql = "select venues.venueName from checkins inner "
				+ "join venues on checkins.venueID = venues.venueID where checkinID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, checkinID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("venueName");
	}

	public HashMap<String, String> getCheckinInfo(String checkinID)
			throws SQLException {

		String sql = "select dateTime, latitude, longitude from checkins where checkinID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, checkinID);
		ResultSet rs = selectStatement.executeQuery();
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

		String sql = "select comments.commentID, comments.userID, users.firstName, "
				+ "users.lastName, comments.comment from comments inner join users on "
				+ "comments.userID = users.userID where comments.checkinID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, checkinID);
		ResultSet rs = selectStatement.executeQuery();
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

		String sql = "select isPublic from users where userID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getBoolean("isPublic");

	}

	public boolean isFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "select userID from friends where (userID = ? and friendUserID = ?) "
				+ " or (friendUserID = ? and userID = ?)";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		selectStatement.setString(2, friendUserID);
		selectStatement.setString(3, friendUserID);
		selectStatement.setString(4, userID);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getVenueWebsite(String checkinID) throws SQLException {

		String sql = "select venues.venueWebsite from checkins inner "
				+ "join venues on checkins.venueID = venues.venueID where checkinID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, checkinID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("venueWebsite");
	}
}
