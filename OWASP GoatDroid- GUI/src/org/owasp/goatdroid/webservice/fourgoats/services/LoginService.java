package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.LoginBean;

public interface LoginService {

	public LoginBean validateCredentials(String userName, String password);

	public LoginBean validateCredentialsAPI(String userName, String password);

	public LoginBean signOut(String sessionToken);

}
