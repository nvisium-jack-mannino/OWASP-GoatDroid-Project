package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.LoginModel;

public interface LoginService {
	public LoginModel isDevicePermanentlyAuthorized(String deviceID);

	public boolean isSessionValid(int sessionToken);

	public LoginModel isSessionValidOrDeviceAuthorized(int sessionToken,
			String deviceID);

	public LoginModel validateCredentials(String userName, String password);

}
