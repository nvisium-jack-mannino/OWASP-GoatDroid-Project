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

import javax.sql.DataSource;

import org.owasp.goatdroid.webservice.fourgoats.model.RewardFieldModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGRewardDaoImpl extends BaseDaoImpl implements RewardDao {

	@Autowired
	public FGRewardDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ArrayList<RewardFieldModel> getAllRewards() throws SQLException {

		String sql = "SELECT app.fg_rewards.rewardName, app.fg_rewards.rewardDescription, "
				+ "app.fg_venues.venueName, app.fg_rewards.checkinsRequired, app.fg_venues.latitude, "
				+ "app.fg_venues.longitude FROM app.fg_rewards INNER JOIN app.fg_venues ON app.fg_rewards.venueID = "
				+ "app.fg_venues.venueID";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql);
		ArrayList<RewardFieldModel> rewards = new ArrayList<RewardFieldModel>();
		while (rs.next()) {
			RewardFieldModel reward = new RewardFieldModel();
			reward.setRewardName(rs.getString("rewardName"));
			reward.setRewardDescription(rs.getString("rewardDescription"));
			reward.setVenueID(rs.getString("venueName"));
			reward.setCheckinsRequired(rs.getInt("checkinsRequired"));
			reward.setLatitude(rs.getString("latitude"));
			reward.setLongitude(rs.getString("longitude"));
			rewards.add(reward);
		}
		return rewards;
	}

	public ArrayList<RewardFieldModel> getEarnedRewards(String userID)
			throws SQLException {

		String sql = "SLEECT app.fg_rewards.rewardName, app.fg_rewards.rewardDescription, app.fg_earned_rewards.timeEarned "
				+ "FROM app.fg_earned_rewards INNER JOIN app.fg_rewards ON app.fg_rewards.rewardID = app.fg_earned_rewards.rewardID "
				+ "WHERE app.fg_earned_rewards.userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		ArrayList<RewardFieldModel> rewards = new ArrayList<RewardFieldModel>();

		while (rs.next()) {
			RewardFieldModel reward = new RewardFieldModel();
			reward.setRewardName(rs.getString("rewardName"));
			reward.setRewardDescription(rs.getString("rewardDescription"));
			reward.setTimeEarned(rs.getString("timeEarned"));
			rewards.add(reward);
		}
		return rewards;
	}

	public void addNewReward(String rewardID, String rewardName,
			String rewardDescription, String venueID, int checkinsRequired)
			throws SQLException {

		String sql = "INSERT INTO app.fg_rewards (rewardID, rewardName, rewardDescription, venueID, checkinsRequired) VALUES (?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { rewardID, rewardName, rewardDescription,
						venueID, checkinsRequired });
	}

	public boolean doesVenueExist(String venueID) throws SQLException {

		String sql = "SELECT venueID FROM app.fg_venues where venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		if (rs.next())
			return true;
		else
			return false;
	}
}
