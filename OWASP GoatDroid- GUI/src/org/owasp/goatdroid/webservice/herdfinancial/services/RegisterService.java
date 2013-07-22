package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.RegisterBean;

public interface RegisterService {

	public RegisterBean registerUser(String accountNumber, String firstName,
			String lastName, String userName, String password);
}
