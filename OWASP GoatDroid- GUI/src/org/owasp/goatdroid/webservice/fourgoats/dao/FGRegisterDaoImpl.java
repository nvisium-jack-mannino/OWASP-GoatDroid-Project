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

import java.sql.SQLException;

import javax.sql.DataSource;

import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGRegisterDaoImpl extends BaseDaoImpl implements RegisterDao {

	@Autowired
	public FGRegisterDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean doesUserExist(String userName) throws SQLException {

		String sql = "SELECT userName FROM app.fg_users WHERE userName = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userName);
		if (rs.next())
			return true;
		else
			return false;
	}

	public void insertNewUser(String firstName, String lastName,
			String userName, String password) throws SQLException {

		String sql = "INSERT INTO app.fg_users (userName, password, firstName, "
				+ "lastName, userID, lastLatitude, "
				+ "lastLongitude, lastCheckinTime, numberOfCheckins, "
				+ "numberOfRewards, isAdmin, autoCheckin, isPublic) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] {
						userName,
						LoginUtils.generateSaltedSHA512Hash(password,
								Salts.PASSWORD_HASH_SALT),
						firstName,
						lastName,
						LoginUtils.generateSaltedSHA256Hash(userName
								+ LoginUtils.getTimeMilliseconds(),
								Salts.USER_ID_GENERATOR_SALT), "0", "0", "", 0,
						0, false, true, true });
	}
}
