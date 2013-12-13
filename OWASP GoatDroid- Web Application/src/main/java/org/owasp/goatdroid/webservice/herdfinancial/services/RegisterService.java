package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.Register;

public interface RegisterService {

	public Register registerUser(String accountNumber, String firstName,
			String lastName, String userName, String password);
}
