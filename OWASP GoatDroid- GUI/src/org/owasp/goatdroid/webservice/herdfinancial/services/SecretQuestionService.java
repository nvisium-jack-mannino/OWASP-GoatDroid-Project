package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.SecretQuestionModel;

public interface SecretQuestionService {

	public SecretQuestionModel setSecretQuestions(int sessionToken,
			String answer1, String answer2, String answer3);
}
