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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizeDAO extends BaseDAO {

	public AuthorizeDAO() {
		super();
	}

	public void authorizeDevice(String deviceID, int sessionToken)
			throws SQLException {
		String sql = "update users set deviceID = ?, isDeviceAuthorized = true where sessionToken = ?";
		PreparedStatement updateAuthorization = (PreparedStatement) conn
				.prepareCall(sql);
		updateAuthorization.setString(1, deviceID);
		updateAuthorization.setInt(2, sessionToken);
		updateAuthorization.executeUpdate();
	}

	public boolean isDeviceAuthorized(String deviceID) throws SQLException {
		String sql = "select isDeviceAuthorized from users where deviceID = ?";
		PreparedStatement selectDeviceAuth = (PreparedStatement) conn
				.prepareCall(sql);
		selectDeviceAuth.setString(1, deviceID);
		ResultSet rs = selectDeviceAuth.executeQuery();
		if (rs.next()) {
			if (rs.getBoolean("isDeviceAuthorized"))

				return true;
			else
				return false;
		} else
			return false;
	}
}
