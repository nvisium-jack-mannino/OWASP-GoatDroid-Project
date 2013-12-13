package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;

public interface SecretQuestionService {

	public BaseModel setSecretQuestions(String authToken,
			String answer1, String answer2, String answer3);
}
