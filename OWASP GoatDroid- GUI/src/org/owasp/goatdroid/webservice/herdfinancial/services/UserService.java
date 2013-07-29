package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.LoginBean;

public interface UserService {

	public LoginBean signOut(int sessionToken);
}
