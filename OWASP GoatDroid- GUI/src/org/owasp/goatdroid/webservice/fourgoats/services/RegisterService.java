package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.RegisterModel;

public interface RegisterService {

	public RegisterModel registerUser(String firstName, String lastName,
			String userName, String password);
}
