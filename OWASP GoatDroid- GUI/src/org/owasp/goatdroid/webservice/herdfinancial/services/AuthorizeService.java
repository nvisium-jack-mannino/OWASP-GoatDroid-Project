package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.AuthorizeModel;

public interface AuthorizeService {

	public AuthorizeModel authorizeDevice(String deviceID, int sessionToken);
}
