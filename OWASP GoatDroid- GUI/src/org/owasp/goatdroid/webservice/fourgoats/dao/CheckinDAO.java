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
import java.util.HashMap;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;

public class CheckinDAO extends BaseDAO {

	public CheckinDAO() {
		super();
	}

	public void insertCheckin(String dateTime, String latitude,
			String longitude, String userID, String venueID, String checkinID)
			throws Exception {

		String sql = "insert into checkins (dateTime, latitude, longitude, userID, venueID, checkinID) "
				+ "values (?,?,?,?,?,?)";
		PreparedStatement insertCheckinEvent = (PreparedStatement) conn
				.prepareCall(sql);
		insertCheckinEvent.setString(1, dateTime);
		insertCheckinEvent.setString(2, latitude);
		insertCheckinEvent.setString(3, longitude);
		insertCheckinEvent.setString(4, userID);
		insertCheckinEvent.setString(5, venueID);
		insertCheckinEvent.setString(6, checkinID);
		insertCheckinEvent.executeUpdate();
	}

	public void updateUserInfo(String latitude, String longitude,
			String dateTime, int totalCheckins, String userID) throws Exception {

		String sql = "update users SET lastLatitude = ?, lastLongitude = ?, lastCheckinTime = ?,"
				+ " numberOfCheckins = ? where userID = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, latitude);
		updateStatement.setString(2, longitude);
		updateStatement.setString(3, dateTime);
		// Increment by 1 to reflect latest checkin
		updateStatement.setInt(4, totalCheckins + 1);
		updateStatement.setString(5, userID);
		updateStatement.executeUpdate();
	}

	public int getTotalCheckins(String userID) throws Exception {

		String sql = "select numberOfCheckins from users where userID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getInt("numberOfCheckins");
	}

	public boolean doesVenueExist(String latitude, String longitude)
			throws Exception {

		String sql = "select venueID from venues where latitude = ? and longitude = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, latitude);
		selectStatement.setString(2, longitude);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getVenueID(String latitude, String longitude)
			throws Exception {

		String sql = "select venueID from venues where latitude = ? and longitude = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, latitude);
		selectStatement.setString(2, longitude);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("venueID");

	}

	public String getVenueName(String venueID) throws Exception {

		String sql = "select venueName from venues where venueID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, venueID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("venueName");
	}

	public boolean doesVenueHaveReward(String venueID) throws Exception {

		String sql = "select rewards.venueID from rewards inner join venues on rewards.venueID = venues.venueID where rewards.venueID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, venueID);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public int getCheckinsAtVenue(String userID, String venueID)
			throws Exception {

		String sql = "select venueID from checkins where userID = ? and venueID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		selectStatement.setString(2, venueID);
		ResultSet rs = selectStatement.executeQuery();
		int count = 0;

		while (rs.next())
			count++;

		return count;
	}

	public String getRewardID(String venueID) throws Exception {

		String sql = "select rewardID from rewards where venueID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, venueID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("rewardID");
	}

	public int getRewardCheckinsRequired(String rewardID) throws Exception {

		String sql = "select checkinsRequired from rewards where rewardID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, rewardID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();

		return rs.getInt("checkinsRequired");

	}

	public void addReward(String userID, String rewardID) throws Exception {

		String sql = "insert into earned_rewards (userID, rewardID, timeEarned) values (?,?,?)";
		PreparedStatement insertStatement = (PreparedStatement) conn
				.prepareCall(sql);
		insertStatement.setString(1, userID);
		insertStatement.setString(2, rewardID);
		insertStatement.setString(3, LoginUtils.getCurrentDateTime());
		insertStatement.executeUpdate();
	}

	public HashMap<String, String> getRewardInfo(String rewardID, String venueID)
			throws Exception {

		String sql = "select venues.venueName, rewards.rewardName, rewards.rewardDescription "
				+ "from rewards inner join venues on rewards.venueID = venues.venueID where "
				+ "rewards.rewardID = ? and rewards.venueID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, rewardID);
		selectStatement.setString(2, venueID);
		ResultSet rs = selectStatement.executeQuery();
		HashMap<String, String> rewardInfo = new HashMap<String, String>();
		if (rs.next()) {
			rewardInfo.put("venueName", rs.getString("venueName"));
			rewardInfo.put("rewardName", rs.getString("rewardName"));
			rewardInfo.put("rewardDescription",
					rs.getString("rewardDescription"));
		}
		return rewardInfo;
	}

	public boolean doesUserHaveReward(String userID, String rewardID)
			throws Exception {

		String sql = "select userID from earned_rewards where userID = ? and rewardID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		selectStatement.setString(2, rewardID);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}
}
