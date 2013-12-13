package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;

public interface ForgotPasswordDao {

	public boolean confirmSecretQuestionAnswer(String userName,
			String secretQuestionIndex, String secretQuestionAnswer)
			throws SQLException;

	public void updatePasswordResetCode(String userName, String code)
			throws SQLException;

	public boolean confirmPasswordResetCode(String userName,
			int passwordResetCode) throws SQLException;

	public void updatePassword(String userName, String password)
			throws SQLException;

	public void clearPasswordResetCode(String userName) throws SQLException;
}
