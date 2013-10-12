package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Rewards extends GenericResponseObject {

	ArrayList<HashMap<String, String>> rewards;

	public ArrayList<HashMap<String, String>> getRewards() {
		return rewards;
	}

	public void setRewards(ArrayList<HashMap<String, String>> rewards) {
		this.rewards = rewards;
	}
}
