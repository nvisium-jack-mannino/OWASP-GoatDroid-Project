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
import org.owasp.goatdroid.webservice.fourgoats.model.RewardModel;

public class RewardDAO extends BaseDAO {

	public RewardDAO() {
		super();
	}

	public ArrayList<RewardModel> getAllRewards() throws SQLException {

		String sql = "select rewards.rewardName, rewards.rewardDescription, "
				+ "venues.venueName, rewards.checkinsRequired, venues.latitude, "
				+ "venues.longitude from rewards inner join venues on rewards.venueID = "
				+ "venues.venueID";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		ResultSet rs = selectStatement.executeQuery();
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
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		ResultSet rs = selectStatement.executeQuery();
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
		PreparedStatement insertStatement = (PreparedStatement) conn
				.prepareCall(sql);
		insertStatement.setString(1, rewardID);
		insertStatement.setString(2, rewardName);
		insertStatement.setString(3, rewardDescription);
		insertStatement.setString(4, venueID);
		insertStatement.setInt(5, checkinsRequired);
		insertStatement.executeUpdate();
	}

	public boolean doesVenueExist(String venueID) throws SQLException {

		String sql = "select venueID from venues where venueID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, venueID);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}
}
