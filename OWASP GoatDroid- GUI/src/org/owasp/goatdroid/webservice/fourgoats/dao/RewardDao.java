package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.owasp.goatdroid.webservice.fourgoats.model._RewardModel;

public interface RewardDao {

	public ArrayList<_RewardModel> getAllRewards() throws SQLException;

	public ArrayList<_RewardModel> getEarnedRewards(String userID)
			throws SQLException;

	public void addNewReward(String rewardID, String rewardName,
			String rewardDescription, String venueID, int checkinsRequired)
			throws SQLException;

	public boolean doesVenueExist(String venueID) throws SQLException;

}
