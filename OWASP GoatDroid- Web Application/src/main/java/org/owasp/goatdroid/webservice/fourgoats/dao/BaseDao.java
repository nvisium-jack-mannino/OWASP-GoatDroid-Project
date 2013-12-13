package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;

public interface BaseDao {

	public boolean checkAuthMatchesUserID(String authToken, String userID)
			throws SQLException;

	public String getUserID(String authToken) throws Exception;

	public String getCheckinOwner(String checkinID) throws Exception;

	public boolean isCheckinOwnerProfilePublic(String checkinID)
			throws Exception;

	public boolean isProfilePublic(String userID) throws Exception;

	public boolean isFriend(String userID, String friendUserID)
			throws Exception;

	public boolean isAdmin(String userID) throws Exception;

	public String getUserNameByAuthToken(String authToken) throws Exception;

	public boolean isAuthValid(String userName, String authToken)
			throws Exception;
}
