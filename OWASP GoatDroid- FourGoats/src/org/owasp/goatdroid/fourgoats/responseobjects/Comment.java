package org.owasp.goatdroid.fourgoats.responseobjects;

public class Comment extends GenericResponseObject {

	String checkinID;
	String venueName;
	String dateTime;

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
}
