package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;

public interface AuthorizeService {

	public BaseModel authorizeDevice(String deviceID, String sessionToken);
}
