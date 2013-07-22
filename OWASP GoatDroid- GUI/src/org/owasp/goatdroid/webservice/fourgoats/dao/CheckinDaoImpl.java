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
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class CheckinDaoImpl extends BaseDaoImpl implements CheckinDao {

	public void insertCheckin(String dateTime, String latitude,
			String longitude, String userID, String venueID, String checkinID)
			throws Exception {

		String sql = "insert into checkins (dateTime, latitude, longitude, userID, venueID, checkinID) "
				+ "values (?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { dateTime, latitude, longitude, userID, venueID,
						checkinID });
	}

	public void updateUserInfo(String latitude, String longitude,
			String dateTime, int totalCheckins, String userID) throws Exception {

		String sql = "update users SET lastLatitude = ?, lastLongitude = ?, lastCheckinTime = ?,"
				+ " numberOfCheckins = ? where userID = ?";
		getJdbcTemplate().update(
				sql,
				new Object[] { latitude, longitude, dateTime,
						totalCheckins + 1, userID });
	}

	public int getTotalCheckins(String userID) throws Exception {

		String sql = "select numberOfCheckins from users where userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		rs.next();
		return rs.getInt("numberOfCheckins");
	}

	public boolean doesVenueExist(String latitude, String longitude)
			throws Exception {

		String sql = "select venueID from venues where latitude = ? and longitude = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { latitude, longitude });
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getVenueID(String latitude, String longitude)
			throws Exception {

		String sql = "select venueID from venues where latitude = ? and longitude = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { latitude, longitude });
		rs.next();
		return rs.getString("venueID");

	}

	public String getVenueName(String venueID) throws Exception {

		String sql = "select venueName from venues where venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		rs.next();
		return rs.getString("venueName");
	}

	public boolean doesVenueHaveReward(String venueID) throws Exception {

		String sql = "select rewards.venueID from rewards inner join venues on rewards.venueID = venues.venueID where rewards.venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		if (rs.next())
			return true;
		else
			return false;
	}

	public int getCheckinsAtVenue(String userID, String venueID)
			throws Exception {

		String sql = "select venueID from checkins where userID = ? and venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, venueID });
		int count = 0;

		while (rs.next())
			count++;

		return count;
	}

	public String getRewardID(String venueID) throws Exception {

		String sql = "select rewardID from rewards where venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		rs.next();
		return rs.getString("rewardID");
	}

	public int getRewardCheckinsRequired(String rewardID) throws Exception {

		String sql = "select checkinsRequired from rewards where rewardID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, rewardID);
		rs.next();

		return rs.getInt("checkinsRequired");

	}

	public void addReward(String userID, String rewardID) throws Exception {

		String sql = "insert into earned_rewards (userID, rewardID, timeEarned) values (?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { userID, rewardID,
						LoginUtils.getCurrentDateTime() });
	}

	public HashMap<String, String> getRewardInfo(String rewardID, String venueID)
			throws Exception {

		String sql = "select venues.venueName, rewards.rewardName, rewards.rewardDescription "
				+ "from rewards inner join venues on rewards.venueID = venues.venueID where "
				+ "rewards.rewardID = ? and rewards.venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { rewardID, venueID });
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
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, rewardID });
		if (rs.next())
			return true;
		else
			return false;
	}
}
