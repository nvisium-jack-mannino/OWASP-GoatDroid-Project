package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

public interface UserDao {

	public void terminateSession(int sessionToken) throws SQLException;
}
