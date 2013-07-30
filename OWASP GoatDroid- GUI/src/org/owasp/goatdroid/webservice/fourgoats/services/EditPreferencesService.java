package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.EditPreferencesModel;
import org.owasp.goatdroid.webservice.fourgoats.model.GetPreferencesModel;

public interface EditPreferencesService {

	public EditPreferencesModel modifyPreferences(String sessionToken,
			boolean autoCheckin, boolean isPublic);

	public GetPreferencesModel getPreferences(String sessionToken);
}
