package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendList extends GenericResponseObject {

	ArrayList<HashMap<String, String>> friends;

	public ArrayList<HashMap<String, String>> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<HashMap<String, String>> friends) {
		this.friends = friends;
	}
}
