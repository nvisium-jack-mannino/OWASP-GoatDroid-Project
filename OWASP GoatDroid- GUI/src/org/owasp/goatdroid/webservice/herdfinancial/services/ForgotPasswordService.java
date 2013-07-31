package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;
import org.owasp.goatdroid.webservice.herdfinancial.model.ForgotPasswordModel;

public interface ForgotPasswordService {

	public BaseModel requestCode(String userName, int secretQuestionIndex,
			String secretQuestionAnswer);

	public BaseModel verifyCode(String userName, int passwordResetCode);

	public BaseModel updatePassword(String userName,
			int passwordResetCode, String password);
}
