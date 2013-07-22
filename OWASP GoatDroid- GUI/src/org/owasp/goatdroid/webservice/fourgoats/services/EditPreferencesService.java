package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.EditPreferencesBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.GetPreferencesBean;

public interface EditPreferencesService {

	public EditPreferencesBean modifyPreferences(String sessionToken,
			boolean autoCheckin, boolean isPublic);

	public GetPreferencesBean getPreferences(String sessionToken);
}
