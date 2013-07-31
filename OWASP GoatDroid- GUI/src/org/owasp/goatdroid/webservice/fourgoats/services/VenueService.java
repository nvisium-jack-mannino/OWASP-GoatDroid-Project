package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.VenueListModel;

public interface VenueService {

	public BaseModel addVenue(String authToken, String venueName,
			String venueWebsite, String latitude, String longitude);

	public VenueListModel getAllVenues(String authToken);

}
