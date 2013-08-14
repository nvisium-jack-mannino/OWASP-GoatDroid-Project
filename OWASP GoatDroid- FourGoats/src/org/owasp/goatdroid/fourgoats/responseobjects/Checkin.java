package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Checkin extends GenericResponseObject {

	String checkinID;
	String venueName;
	String dateTime;
	String latitude;
	String longitude;
	ArrayList<HashMap<String, String>> rewards;

	public String getCheckinID() {
		return checkinID;
	}

	public void setCheckinID(String checkinID) {
		this.checkinID = checkinID;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public ArrayList<HashMap<String, String>> getRewards() {
		return rewards;
	}

	public void setRewards(ArrayList<HashMap<String, String>> rewards) {
		this.rewards = rewards;
	}
}
