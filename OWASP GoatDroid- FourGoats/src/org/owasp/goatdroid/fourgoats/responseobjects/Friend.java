package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Friend extends GenericResponseObject {

	ArrayList<HashMap<String, String>> profile;

	public ArrayList<HashMap<String, String>> getProfile() {
		return profile;
	}

	public void setProfile(ArrayList<HashMap<String, String>> profile) {
		this.profile = profile;
	}
}
