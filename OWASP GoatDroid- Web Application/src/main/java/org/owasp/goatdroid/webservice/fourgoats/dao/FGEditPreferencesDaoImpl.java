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
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGEditPreferencesDaoImpl extends BaseDaoImpl implements
		EditPreferencesDao {

	@Autowired
	public FGEditPreferencesDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void updatePreferences(boolean autoCheckin, boolean isPublic,
			String userID) throws SQLException {

		String sql = "UPDATE app.fg_users SET autoCheckin = ?, isPublic = ? WHERE userID = ?";
		getJdbcTemplate().update(sql,
				new Object[] { autoCheckin, isPublic, userID });
	}

	public HashMap<String, Boolean> getPreferences(String userID)
			throws SQLException {

		String sql = "SELECT autoCheckin, isPublic FROM app.fg_users WHERE userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		HashMap<String, Boolean> preferences = new HashMap<String, Boolean>();
		rs.next();
		preferences.put("autoCheckin", rs.getBoolean("autoCheckin"));
		preferences.put("isPublic", rs.getBoolean("isPublic"));
		return preferences;
	}
}
