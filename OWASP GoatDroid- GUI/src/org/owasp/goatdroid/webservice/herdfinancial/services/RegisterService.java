package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.RegisterModel;

public interface RegisterService {

	public RegisterModel registerUser(String accountNumber, String firstName,
			String lastName, String userName, String password);
}
