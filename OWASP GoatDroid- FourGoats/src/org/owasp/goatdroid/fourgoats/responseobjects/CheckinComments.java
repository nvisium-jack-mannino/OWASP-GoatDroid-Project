package org.owasp.goatdroid.fourgoats.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckinComments extends GenericResponseObject {

	String venueName;
	String venueWebsite;

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getVenueWebsite() {
		return venueWebsite;
	}

	public void setVenueWebsite(String venueWebsite) {
		this.venueWebsite = venueWebsite;
	}

	ArrayList<HashMap<String, String>> comments;

	public ArrayList<HashMap<String, String>> getComments() {
		return comments;
	}

	public void setComments(ArrayList<HashMap<String, String>> comments) {
		this.comments = comments;
	}
}
