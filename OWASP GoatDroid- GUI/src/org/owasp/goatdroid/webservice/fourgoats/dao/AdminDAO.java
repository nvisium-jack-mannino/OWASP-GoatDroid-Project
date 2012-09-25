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
import java.util.ArrayList;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.model.UserModel;

public class AdminDAO extends BaseDAO {

	public AdminDAO() {
		super();
	}

	public boolean isAdmin(String sessionToken) throws Exception {

		String sql = "select * from users where sessionToken = ? and isAdmin = true";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, sessionToken);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public void deleteUser(String userName) throws Exception {

		String sql = "delete from users where userName = ?";
		PreparedStatement deleteStatement = (PreparedStatement) conn
				.prepareCall(sql);
		deleteStatement.setString(1, userName);
		deleteStatement.executeUpdate();
	}

	public void updatePassword(String userName, String newPassword)
			throws Exception {

		String sql = "update users SET password = ? where userName = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, LoginUtils.generateSaltedSHA512Hash(
				newPassword, Salts.PASSWORD_HASH_SALT));

		updateStatement.setString(2, userName);
		updateStatement.executeUpdate();
	}

	public ArrayList<UserModel> getUsers() throws Exception {

		String sql = "select userName, firstName, lastName from users";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		ResultSet rs = selectStatement.executeQuery();
		ArrayList<UserModel> users = new ArrayList<UserModel>();
		while (rs.next()) {
			UserModel user = new UserModel();
			user.setUserName(rs.getString("userName"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			users.add(user);
		}
		return users;
	}
}
