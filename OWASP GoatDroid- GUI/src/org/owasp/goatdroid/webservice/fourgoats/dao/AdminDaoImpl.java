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

import java.util.ArrayList;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.model.UserModel;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class AdminDaoImpl extends BaseDaoImpl implements AdminDao {

	public boolean isAdmin(String sessionToken) throws Exception {

		String sql = "select * from users where sessionToken = ? and isAdmin = true";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		if (rs.next())
			return true;
		else
			return false;
	}

	public void deleteUser(String userName) throws Exception {

		String sql = "delete from users where userName = ?";
		getJdbcTemplate().update(sql, userName);
	}

	public void updatePassword(String userName, String newPassword)
			throws Exception {

		String sql = "update users SET password = ? where userName = ?";
		getJdbcTemplate().update(
				sql,
				new Object[] {
						LoginUtils.generateSaltedSHA512Hash(newPassword,
								Salts.PASSWORD_HASH_SALT), userName });
	}

	public ArrayList<UserModel> getUsers() throws Exception {

		String sql = "select userName, firstName, lastName from users";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql);
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
