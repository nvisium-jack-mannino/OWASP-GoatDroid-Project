package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Admin extends GenericResponseObject {

	ArrayList<HashMap<String, String>> users;

	public ArrayList<HashMap<String, String>> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<HashMap<String, String>> users) {
		this.users = users;
	}
}
