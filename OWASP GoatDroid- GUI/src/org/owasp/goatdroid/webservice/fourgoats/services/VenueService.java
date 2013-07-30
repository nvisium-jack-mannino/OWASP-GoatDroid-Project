package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.VenueModel;
import org.owasp.goatdroid.webservice.fourgoats.model.VenueListModel;

public interface VenueService {

	public VenueModel addVenue(String sessionToken, String venueName,
			String venueWebsite, String latitude, String longitude);

	public VenueListModel getAllVenues(String sessionToken);

}
