package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.ForgotPasswordModel;

public interface ForgotPasswordService {

	public ForgotPasswordModel requestCode(String userName,
			int secretQuestionIndex, String secretQuestionAnswer);

	public ForgotPasswordModel verifyCode(String userName, int passwordResetCode);

	public ForgotPasswordModel updatePassword(String userName,
			int passwordResetCode, String password);
}
