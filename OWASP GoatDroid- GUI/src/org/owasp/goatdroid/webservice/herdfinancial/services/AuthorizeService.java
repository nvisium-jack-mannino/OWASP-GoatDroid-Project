package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.bean.AuthorizeBean;

public interface AuthorizeService {

	public AuthorizeBean authorizeDevice(String deviceID, int sessionToken);
}
