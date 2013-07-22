package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.RegisterBean;

public interface RegisterService {

	public RegisterBean registerUser(String firstName, String lastName,
			String userName, String password);
}
