/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;

public class RegisterDAO extends BaseDAO {

	public RegisterDAO() {
		super();
	}

	public boolean doesUserExist(String userName) throws SQLException {

		String sql = "select userName from users where userName = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userName);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public void insertNewUser(String firstName, String lastName,
			String userName, String password) throws SQLException {

		String sql = "insert into users (userName, password, firstName, "
				+ "lastName, userID, lastLatitude, "
				+ "lastLongitude, lastCheckinTime, numberOfCheckins, "
				+ "numberOfRewards, isAdmin, autoCheckin, isPublic) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertStatement = (PreparedStatement) conn
				.prepareCall(sql);
		insertStatement.setString(1, userName);
		insertStatement.setString(2, LoginUtils.generateSaltedSHA512Hash(
				password, Salts.PASSWORD_HASH_SALT));
		insertStatement.setString(3, firstName);
		insertStatement.setString(4, lastName);
		/*
		 * Generates the userID unique identifier
		 */
		insertStatement.setString(5, LoginUtils.generateSaltedSHA256Hash(
				userName + LoginUtils.getTimeMilliseconds(),
				Salts.USER_ID_GENERATOR_SALT));
		// latitude
		insertStatement.setString(6, "0");
		// longitude
		insertStatement.setString(7, "0");
		// lastCheckinTime
		insertStatement.setString(8, "");
		// numberOfCheckins
		insertStatement.setInt(9, 0);
		// numberOfRewards
		insertStatement.setInt(10, 0);
		// isAdmin
		insertStatement.setBoolean(11, false);
		// autoCheckin
		insertStatement.setBoolean(12, true);
		// isPublic
		insertStatement.setBoolean(13, true);
		insertStatement.executeUpdate();
	}
}
