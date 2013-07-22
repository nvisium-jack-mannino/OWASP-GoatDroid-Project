package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.util.HashMap;

public interface CheckinDao {

	public void insertCheckin(String dateTime, String latitude,
			String longitude, String userID, String venueID, String checkinID)
			throws Exception;

	public void updateUserInfo(String latitude, String longitude,
			String dateTime, int totalCheckins, String userID) throws Exception;

	public int getTotalCheckins(String userID) throws Exception;

	public boolean doesVenueExist(String latitude, String longitude)
			throws Exception;

	public String getVenueID(String latitude, String longitude)
			throws Exception;

	public String getVenueName(String venueID) throws Exception;

	public boolean doesVenueHaveReward(String venueID) throws Exception;

	public int getCheckinsAtVenue(String userID, String venueID)
			throws Exception;

	public String getRewardID(String venueID) throws Exception;

	public int getRewardCheckinsRequired(String rewardID) throws Exception;

	public void addReward(String userID, String rewardID) throws Exception;

	public HashMap<String, String> getRewardInfo(String rewardID, String venueID)
			throws Exception;

	public boolean doesUserHaveReward(String userID, String rewardID)
			throws Exception;
}
