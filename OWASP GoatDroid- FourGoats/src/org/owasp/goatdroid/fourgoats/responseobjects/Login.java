package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.HashMap;

public class Login extends BaseResponseObject {

	String username;
	String authToken;

	public HashMap<String, String> preferences;

	public Login() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public HashMap<String, String> getPreferences() {
		return preferences;
	}

	public void setPreferences(HashMap<String, String> preferences) {
		this.preferences = preferences;
	}
}
