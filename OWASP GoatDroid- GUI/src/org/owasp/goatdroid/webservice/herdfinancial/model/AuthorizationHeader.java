package org.owasp.goatdroid.webservice.herdfinancial.model;

public class AuthorizationHeader extends BaseModel {

	String username;
	String authToken;

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

}
