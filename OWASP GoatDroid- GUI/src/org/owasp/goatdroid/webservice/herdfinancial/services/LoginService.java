package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.Login;

public interface LoginService {
	public Login isDevicePermanentlyAuthorized(String deviceID);

	public Login isAuthValidOrDeviceAuthorized(String authToken,
			String deviceID);

	public Login validateCredentials(String userName, String password);

}
