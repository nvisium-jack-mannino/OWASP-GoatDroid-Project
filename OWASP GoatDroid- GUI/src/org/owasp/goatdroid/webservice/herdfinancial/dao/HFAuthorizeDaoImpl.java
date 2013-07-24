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
package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class HFAuthorizeDaoImpl extends BaseDaoImpl implements AuthorizeDao {

	@Autowired
	public HFAuthorizeDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void authorizeDevice(String deviceID, int sessionToken)
			throws SQLException {
		String sql = "update users set deviceID = ?, isDeviceAuthorized = true where sessionToken = ?";
		getJdbcTemplate().update(sql, new Object[] { deviceID, sessionToken });
	}

	public boolean isDeviceAuthorized(String deviceID) throws SQLException {
		String sql = "select isDeviceAuthorized from users where deviceID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, deviceID);
		if (rs.next()) {
			if (rs.getBoolean("isDeviceAuthorized"))

				return true;
			else
				return false;
		} else
			return false;
	}
}
