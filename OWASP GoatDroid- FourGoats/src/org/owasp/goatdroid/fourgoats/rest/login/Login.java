package org.owasp.goatdroid.fourgoats.rest.login;

import java.util.ArrayList;
import java.util.HashMap;

public class Login {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username;
	String authToken;
	boolean success;
	public HashMap<String, String> preferences;
	ArrayList<String> errors;

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

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public HashMap<String, String> getPreferences() {
		return preferences;
	}

	public void setPreferences(HashMap<String, String> preferences) {
		this.preferences = preferences;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
}
