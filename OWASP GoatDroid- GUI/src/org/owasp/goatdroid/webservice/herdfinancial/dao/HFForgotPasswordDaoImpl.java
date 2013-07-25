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
public class HFForgotPasswordDaoImpl extends BaseDaoImpl implements
		ForgotPasswordDao {

	@Autowired
	public HFForgotPasswordDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean confirmSecretQuestionAnswer(String userName,
			String secretQuestionIndex, String secretQuestionAnswer)
			throws SQLException {

		String sql = "";

		if (secretQuestionIndex.equals("1"))
			sql = "SELECT answer1 FROM app.hf_users WHERE userName = ?";
		else if (secretQuestionIndex.equals("2"))
			sql = "SELECT answer2 FROM app.hf_users WHERE userName = ?";
		else
			sql = "SELECT answer3 FROM app.hf_users WHERE userName = ?";

		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userName);

		rs.next();
		if ((rs.getString("answer" + secretQuestionIndex)
				.equals(secretQuestionAnswer)))
			return true;
		else
			return false;
	}

	public void updatePasswordResetCode(String userName, String code)
			throws SQLException {

		String sql = "UPDATE app.hf_users SET passwordResetCode = ? WHERE userName = ?";
		getJdbcTemplate().update(sql, new Object[] { userName, code });
	}

	public boolean confirmPasswordResetCode(String userName,
			int passwordResetCode) throws SQLException {

		String sql = "SELECT passwordResetCode FROM app.hf_users WHERE userName = ? AND passwordResetCode = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userName, passwordResetCode });
		if (rs.next())
			return true;
		else
			return false;
	}

	public void updatePassword(String userName, String password)
			throws SQLException {

		String sql = "UPDATE app.hf_users SET password = ? WHERE userName = ?";
		getJdbcTemplate().update(sql, new Object[] { password, userName });
	}

	public void clearPasswordResetCode(String userName) throws SQLException {

		String sql = "UPDATE app.hf_users SET passwordResetCode = '' WHERE userName = ?";
		getJdbcTemplate().update(sql, userName);
	}
}
