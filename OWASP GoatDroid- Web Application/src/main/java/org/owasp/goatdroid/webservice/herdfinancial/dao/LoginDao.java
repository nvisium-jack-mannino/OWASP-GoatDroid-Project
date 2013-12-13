package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

public interface LoginDao {

	public void updateAuth(String userName, String authToken)
			throws SQLException;

	public boolean validateCredentials(String userName, String password)
			throws SQLException;

	public void updateAuthorizedDeviceAuth(String deviceID, String authToken)
			throws SQLException;

	public String getUserName(String authToken) throws SQLException;

	public String getAccountNumber(String authToken) throws SQLException;

	public boolean isDevicePermanentlyAuthorized(String deviceID)
			throws SQLException;
}
