package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.HashMap;

public interface EditPreferencesDao {

	public void updatePreferences(boolean autoCheckin, boolean isPublic,
			String userID) throws SQLException;

	public HashMap<String, Boolean> getPreferences(String userID)
			throws SQLException;
}
