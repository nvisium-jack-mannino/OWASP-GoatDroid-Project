package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.LoginModel;

public interface LoginService {
	public LoginModel isDevicePermanentlyAuthorized(String deviceID);

	public LoginModel isAuthValidOrDeviceAuthorized(String authToken,
			String deviceID);

	public LoginModel validateCredentials(String userName, String password);

}
