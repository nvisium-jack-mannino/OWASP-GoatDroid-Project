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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;

public class BaseDAO {

	protected Connection conn;

	public void closeConnection() throws SQLException {
		conn.close();
	}

	public void openConnection() throws Exception {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = (Connection) DriverManager
					.getConnection(Constants.DB_CONNECTION_STRING);
		} catch (InstantiationException e) {
			throw new Exception();
		} catch (IllegalAccessException e) {
			throw new Exception();
		} catch (ClassNotFoundException e) {
			throw new Exception();
		} catch (SQLException e) {
			throw new Exception();
		}
	}
}
