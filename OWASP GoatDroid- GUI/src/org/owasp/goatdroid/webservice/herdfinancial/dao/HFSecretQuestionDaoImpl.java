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
import org.springframework.stereotype.Repository;

@Repository
public class HFSecretQuestionDaoImpl extends BaseDaoImpl implements
		SecretQuestionDao {

	@Autowired
	public HFSecretQuestionDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void updateAnswers(int sessionToken, String answer1, String answer2,
			String answer3) throws SQLException {

		String sql = "update users SET answer1 = ?, answer2 = ?, answer3 = ? where sessionToken = ?";
		getJdbcTemplate().update(sql,
				new Object[] { answer1, answer2, answer3, sessionToken });
	}
}
