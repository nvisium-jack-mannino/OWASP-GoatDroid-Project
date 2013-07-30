package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.owasp.goatdroid.webservice.fourgoats.model._HistoryModel;

public interface HistoryDao {

	public ArrayList<_HistoryModel> getCheckinHistory(String userID)
			throws SQLException;

	public ArrayList<_HistoryModel> getCheckinHistoryByUserName(String userName)
			throws SQLException;

	public String getVenueName(String checkinID) throws SQLException;

	public HashMap<String, String> getCheckinInfo(String checkinID)
			throws SQLException;

	public HashMap<String, String> selectComments(String checkinID)
			throws SQLException;

	public boolean isProfilePublic(String userID) throws SQLException;

	public boolean isFriend(String userID, String friendUserID)
			throws SQLException;

	public String getVenueWebsite(String checkinID) throws SQLException;

}
