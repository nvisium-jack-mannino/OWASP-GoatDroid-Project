package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

public interface RegisterDao {

	public void registerUser(String accountNumber, String firstName,
			String lastName, String userName, String password)
			throws SQLException;

	public boolean doesUserNameExist(String userName) throws SQLException;

	public boolean doesAccountNumberExist(String accountNumber)
			throws SQLException;
}
