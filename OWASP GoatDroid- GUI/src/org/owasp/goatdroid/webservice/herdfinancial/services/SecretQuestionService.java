package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.SecretQuestionBean;

public interface SecretQuestionService {

	public SecretQuestionBean setSecretQuestions(int sessionToken,
			String answer1, String answer2, String answer3);
}
