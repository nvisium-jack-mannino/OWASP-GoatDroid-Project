package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.Checkin;

public interface CheckinService {

	public Checkin doCheckin(String authToken, String latitude,
			String longitude);
}
