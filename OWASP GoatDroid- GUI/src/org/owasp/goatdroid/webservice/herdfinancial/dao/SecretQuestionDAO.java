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
import java.sql.SQLException;

public class SecretQuestionDAO extends BaseDAO {

	public SecretQuestionDAO() {
		super();
	}

	public void updateAnswers(int sessionToken, String answer1, String answer2,
			String answer3) throws SQLException {

		String sql = "update users SET answer1 = ?, answer2 = ?, answer3 = ? where sessionToken = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, answer1);
		updateStatement.setString(2, answer2);
		updateStatement.setString(3, answer3);
		updateStatement.setInt(4, sessionToken);
		updateStatement.executeUpdate();
	}
}
