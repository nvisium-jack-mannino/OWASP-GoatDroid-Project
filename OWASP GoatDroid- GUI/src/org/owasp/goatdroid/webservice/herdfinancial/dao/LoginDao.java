package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

public interface LoginDao {

	public void updateSession(String userName, int sessionToken,
			long sessionStartTime) throws SQLException;

	public long getSessionStartTime(int sessionToken) throws SQLException;

	public boolean validateCredentials(String userName, String password)
			throws SQLException;

	public void updateAuthorizedDeviceSession(String deviceID,
			int sessionToken, long sessionStartTime) throws SQLException;

	public String getUserName(int sessionToken) throws SQLException;

	public String getAccountNumber(int sessionToken) throws SQLException;

	public boolean isDevicePermanentlyAuthorized(String deviceID)
			throws SQLException;
}
