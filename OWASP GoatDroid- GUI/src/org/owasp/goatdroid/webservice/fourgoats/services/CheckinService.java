package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.CheckinBean;

public interface CheckinService {

	public CheckinBean doCheckin(String sessionToken, String latitude,
			String longitude);
}
