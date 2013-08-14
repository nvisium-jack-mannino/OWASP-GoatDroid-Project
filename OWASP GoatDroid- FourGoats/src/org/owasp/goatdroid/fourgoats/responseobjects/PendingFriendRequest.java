package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class PendingFriendRequest extends GenericResponseObject {

	ArrayList<HashMap<String, String>> pendingFriendRequests;

	public ArrayList<HashMap<String, String>> getPendingFriendRequests() {
		return pendingFriendRequests;
	}

	public void setPendingFriendRequests(
			ArrayList<HashMap<String, String>> pendingFriendRequests) {
		this.pendingFriendRequests = pendingFriendRequests;
	}
}
