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

public class ForgotPasswordDAO extends BaseDAO {

	public ForgotPasswordDAO() {
		super();
	}

	public boolean confirmSecretQuestionAnswer(String userName,
			String secretQuestionIndex, String secretQuestionAnswer)
			throws SQLException {

		String sql = "";

		if (secretQuestionIndex.equals("1"))
			sql = "SELECT answer1 FROM users WHERE userName = ?";
		else if (secretQuestionIndex.equals("2"))
			sql = "SELECT answer2 FROM users WHERE userName = ?";
		else
			sql = "SELECT answer3 FROM users WHERE userName = ?";

		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userName);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		if ((rs.getString("answer" + secretQuestionIndex)
				.equals(secretQuestionAnswer)))
			return true;
		else
			return false;
	}

	public void updatePasswordResetCode(String userName, String code)
			throws SQLException {

		String sql = "UPDATE users SET passwordResetCode = ? WHERE userName = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, code);
		updateStatement.setString(2, userName);
		updateStatement.executeUpdate();
	}

	public boolean confirmPasswordResetCode(String userName,
			int passwordResetCode) throws SQLException {

		String sql = "SELECT passwordResetCode FROM users WHERE userName = ? and passwordResetCode = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userName);
		selectStatement.setInt(2, passwordResetCode);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public void updatePassword(String userName, String password)
			throws SQLException {

		String sql = "UPDATE users SET password = ? where userName = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, password);
		updateStatement.setString(2, userName);
		updateStatement.executeUpdate();
	}

	public void clearPasswordResetCode(String userName) throws SQLException {

		String sql = "UPDATE users SET passwordResetCode = '' WHERE userName = ?";
		PreparedStatement updateStatement = (PreparedStatement) conn
				.prepareCall(sql);
		updateStatement.setString(1, userName);
		updateStatement.executeUpdate();
	}
}
