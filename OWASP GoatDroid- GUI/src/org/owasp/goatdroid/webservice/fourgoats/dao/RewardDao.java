package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.owasp.goatdroid.webservice.fourgoats.model.RewardFieldModel;

public interface RewardDao {

	public ArrayList<RewardFieldModel> getAllRewards() throws SQLException;

	public ArrayList<RewardFieldModel> getEarnedRewards(String userID)
			throws SQLException;

	public void addNewReward(String rewardID, String rewardName,
			String rewardDescription, String venueID, int checkinsRequired)
			throws SQLException;

	public boolean doesVenueExist(String venueID) throws SQLException;

}
