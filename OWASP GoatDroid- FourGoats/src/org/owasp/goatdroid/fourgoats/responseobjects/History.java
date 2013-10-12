package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class History extends GenericResponseObject {

	ArrayList<HashMap<String, String>> history;

	public ArrayList<HashMap<String, String>> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<HashMap<String, String>> history) {
		this.history = history;
	}
}
