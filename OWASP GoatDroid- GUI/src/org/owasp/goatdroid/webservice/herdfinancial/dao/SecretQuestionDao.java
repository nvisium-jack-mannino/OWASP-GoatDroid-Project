package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

public interface SecretQuestionDao {

	public void updateAnswers(String authToken, String answer1, String answer2,
			String answer3) throws SQLException;
}
