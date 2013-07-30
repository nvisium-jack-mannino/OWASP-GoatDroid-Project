package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.LoginModel;

public interface LoginService {

	public LoginModel validateCredentials(String userName, String password);

	public LoginModel validateCredentialsAPI(String userName, String password);

}
