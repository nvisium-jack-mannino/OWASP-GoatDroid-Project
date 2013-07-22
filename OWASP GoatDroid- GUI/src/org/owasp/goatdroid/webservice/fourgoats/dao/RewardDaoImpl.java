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
import org.owasp.goatdroid.webservice.fourgoats.model.RewardModel;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class RewardDaoImpl extends BaseDaoImpl implements RewardDao {

	public ArrayList<RewardModel> getAllRewards() throws SQLException {

		String sql = "select rewards.rewardName, rewards.rewardDescription, "
				+ "venues.venueName, rewards.checkinsRequired, venues.latitude, "
				+ "venues.longitude from rewards inner join venues on rewards.venueID = "
				+ "venues.venueID";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql);
		ArrayList<RewardModel> rewards = new ArrayList<RewardModel>();
		while (rs.next()) {
			RewardModel reward = new RewardModel();
			reward.setRewardName(rs.getString("rewardName"));
			reward.setRewardDescription(rs.getString("rewardDescription"));
			reward.setVenueName(rs.getString("venueName"));
			reward.setCheckinsRequired(Integer.toString(rs
					.getInt("checkinsRequired")));
			reward.setLatitude(rs.getString("latitude"));
			reward.setLongitude(rs.getString("longitude"));
			rewards.add(reward);
		}
		return rewards;
	}

	public ArrayList<RewardModel> getEarnedRewards(String userID)
			throws SQLException {

		String sql = "select rewards.rewardName, rewards.rewardDescription, earned_rewards.timeEarned "
				+ "from earned_rewards inner join rewards on rewards.rewardID = earned_rewards.rewardID "
				+ "where earned_rewards.userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		ArrayList<RewardModel> rewards = new ArrayList<RewardModel>();

		while (rs.next()) {
			RewardModel reward = new RewardModel();
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

		String sql = "insert into rewards (rewardID, rewardName, rewardDescription, venueID, checkinsRequired) values (?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { rewardID, rewardName, rewardDescription,
						venueID, checkinsRequired });
	}

	public boolean doesVenueExist(String venueID) throws SQLException {

		String sql = "select venueID from venues where venueID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, venueID);
		if (rs.next())
			return true;
		else
			return false;
	}
}
