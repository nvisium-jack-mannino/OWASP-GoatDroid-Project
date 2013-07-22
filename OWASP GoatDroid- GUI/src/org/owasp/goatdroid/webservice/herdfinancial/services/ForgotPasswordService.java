package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.ForgotPasswordBean;

public interface ForgotPasswordService {

	public ForgotPasswordBean requestCode(String userName,
			int secretQuestionIndex, String secretQuestionAnswer);

	public ForgotPasswordBean verifyCode(String userName, int passwordResetCode);

	public ForgotPasswordBean updatePassword(String userName,
			int passwordResetCode, String password);
}
