package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;

public interface RegisterDao {

	public boolean doesUserExist(String userName) throws SQLException;

	public void insertNewUser(String firstName, String lastName,
			String userName, String password) throws SQLException;
}
