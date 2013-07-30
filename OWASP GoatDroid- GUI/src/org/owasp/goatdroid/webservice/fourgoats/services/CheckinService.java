package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.CheckinModel;

public interface CheckinService {

	public CheckinModel doCheckin(String sessionToken, String latitude,
			String longitude);
}
