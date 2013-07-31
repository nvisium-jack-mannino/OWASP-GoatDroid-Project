package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;

public interface EditPreferencesService {

	public BaseModel modifyPreferences(String authToken,
			boolean autoCheckin, boolean isPublic);

	public BaseModel getPreferences(String authToken);
}
