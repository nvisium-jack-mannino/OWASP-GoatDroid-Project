package org.owasp.goatdroid.fourgoats.rest.login;

import java.io.Serializable;
import java.util.HashMap;

public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username;
	String authToken;
	boolean success;
	public HashMap<String, Boolean> preferences;

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

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public HashMap<String, Boolean> getPreferences() {
		return preferences;
	}

	public void setPreferences(HashMap<String, Boolean> preferences) {
		this.preferences = preferences;
	}
}
