package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

public interface AuthorizeDao {

	public void authorizeDevice(String deviceID, String authToken)
			throws SQLException;

	public boolean isDeviceAuthorized(String deviceID) throws SQLException;

}
