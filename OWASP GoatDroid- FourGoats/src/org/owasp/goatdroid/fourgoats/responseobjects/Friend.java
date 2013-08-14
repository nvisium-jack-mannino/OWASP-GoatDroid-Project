package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.HashMap;

public class Friend extends GenericResponseObject {

	HashMap<String, String> profile;

	public HashMap<String, String> getProfile() {
		return profile;
	}

	public void setProfile(HashMap<String, String> profile) {
		this.profile = profile;
	}
}
