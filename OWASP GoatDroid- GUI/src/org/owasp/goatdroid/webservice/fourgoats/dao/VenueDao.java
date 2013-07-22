package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.HashMap;

public interface VenueDao {

	public boolean doesVenueExist(String venueName, String latitude,
			String longitude) throws SQLException;

	public void insertNewVenue(String venueID, String venueName,
			String venueWebsite, String latitude, String longitude)
			throws SQLException;

	public HashMap<String, String> getAllVenues() throws SQLException;
}
