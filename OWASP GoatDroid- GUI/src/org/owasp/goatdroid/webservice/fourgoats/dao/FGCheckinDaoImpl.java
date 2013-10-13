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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGCheckinDaoImpl extends BaseDaoImpl implements CheckinDao {

	@Autowired
	public FGCheckinDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void insertCheckin(String dateTime, String latitude,
			String longitude, String userID, String venueID, String checkinID)
			throws Exception {

		String sql = "INSERT INTO app.fg_checkins (dateTime, latitude, longitude, userID, venueID, checkinID) "
				+ "VALUES (?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { dateTime, latitude, longitude, userID, venueID,
						checkinID });
	}

	public void updateUserInfo(String latitude, String longitude,
			String dateTime, int totalCheckins, String userID) throws Exception {

		String sql = "UPDATE app.fg_users SET lastLatitude = ?, lastLongitude = ?, lastCheckinTime = ?,"
				+ " numberOfCheckins = ? WHERE userID = ?";
		getJdbcTemplate().update(
				sql,
				new Object[] { latitude, longitude, dateTime,
						totalCheckins + 1, userID });
	}

	public int getTotalCheckins(String userID) throws Exception {

		String sql = "SELECT numberOfCheckins FROM app.fg_users WHERE userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		rs.next();
		return rs.getInt("numberOfCheckins");
	}

	public boolean doesVenueExist(String latitude, String longitude)
			throws Exception {

		String sql = "SELECT venueID FROM app.fg_venues WHERE latitude = ? AND longitude = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { latitude, longitude });
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getVenueID(String latitude, String longitude)
			throws Exception {

		String sql = "SELECT venueID FROM app.fg_venues WHERE latitude = ? AND longitude = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { latitude, longitude });
		rs.next();
		return rs.getString("venueID");

	}

	public String getVenueName(String venueID) throws Exception {

		String sql = "SELECT venueName FROM app.fg_venues WHERE venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		rs.next();
		return rs.getString("venueName");
	}

	public boolean doesVenueHaveReward(String venueID) throws Exception {

		String sql = "SELECT app.fg_rewards.venueID FROM app.fg_rewards INNER JOIN app.fg_venues ON "
				+ "app.fg_rewards.venueID = app.fg_venues.venueID WHERE app.fg_rewards.venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		if (rs.next())
			return true;
		else
			return false;
	}

	public int getCheckinsAtVenue(String userID, String venueID)
			throws Exception {

		String sql = "SELECT venueID FROM app.fg_checkins WHERE userID = ? AND venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, venueID });
		int count = 0;

		while (rs.next())
			count++;

		return count;
	}

	public String getRewardID(String venueID) throws Exception {

		String sql = "SELECT rewardID FROM app.fg_rewards WHERE venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		rs.next();
		return rs.getString("rewardID");
	}

	public int getRewardCheckinsRequired(String rewardID) throws Exception {

		String sql = "SELECT checkinsRequired FROM app.fg_rewards WHERE rewardID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, rewardID);
		rs.next();

		return rs.getInt("checkinsRequired");

	}

	public void addReward(String userID, String rewardID) throws Exception {

		String sql = "INSERT INTO app.fg_earned_rewards (userID, rewardID, timeEarned) VALUES (?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { userID, rewardID,
						LoginUtils.getCurrentDateTime() });
	}

	public HashMap<String, String> getRewardInfo(String rewardID, String venueID)
			throws Exception {

		String sql = "SELECT app.fg_venues.venueName, app.fg_rewards.rewardName, app.fg_rewards.rewardDescription "
				+ "FROM app.fg_rewards INNER JOIN app.fg_venues on app.fg_rewards.venueID = app.fg_venues.venueID WHERE "
				+ "app.fg_rewards.rewardID = ? AND app.fg_rewards.venueID = ?";
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

		String sql = "SELECT userID FROM app.fg_earned_rewards WHERE userID = ? AND rewardID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, rewardID });
		if (rs.next())
			return true;
		else
			return false;
	}
}
