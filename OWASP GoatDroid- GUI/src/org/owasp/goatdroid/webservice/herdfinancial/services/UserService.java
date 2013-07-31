package org.owasp.goatdroid.webservice.herdfinancial.services;

import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;

public interface UserService {

	public BaseModel signOut(String authToken);
}
