package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.VenueBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.VenueListBean;

public interface VenueService {

	public VenueBean addVenue(String sessionToken, String venueName,
			String venueWebsite, String latitude, String longitude);

	public VenueListBean getAllVenues(String sessionToken);

}
