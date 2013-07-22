package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;

public interface BaseDao {

	public boolean checkSessionMatchesUserID(String sessionToken, String userID)
			throws SQLException;

	public String getUserID(String sessionToken) throws Exception;

	public String getCheckinOwner(String checkinID) throws Exception;

	public boolean isCheckinOwnerProfilePublic(String checkinID)
			throws Exception;

	public boolean isProfilePublic(String userID) throws Exception;

	public boolean isFriend(String userID, String friendUserID)
			throws Exception;

	public boolean isAdmin(String userID) throws Exception;

	public String getUserNameBySessionToken(String sessionToken)
			throws Exception;

	public long getSessionStartTime(String sessionToken) throws Exception;

	public boolean isSessionValid(String sessionToken) throws Exception;
}
