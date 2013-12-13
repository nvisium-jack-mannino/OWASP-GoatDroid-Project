package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;

public interface RegisterService {

	public BaseModel registerUser(String firstName, String lastName,
			String userName, String password);
}
