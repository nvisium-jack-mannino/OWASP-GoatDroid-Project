package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.LoginModel;

public interface UserService {

	public LoginModel signOut(int sessionToken);
}
