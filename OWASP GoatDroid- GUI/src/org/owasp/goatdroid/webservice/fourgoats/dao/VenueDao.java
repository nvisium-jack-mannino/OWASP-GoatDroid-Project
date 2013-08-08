package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.owasp.goatdroid.webservice.fourgoats.model.Venue;

public interface VenueDao {

	public boolean doesVenueExist(String venueName, String latitude,
			String longitude) throws SQLException;

	public void insertNewVenue(String venueID, String venueName,
			String venueWebsite, String latitude, String longitude)
			throws SQLException;

	public ArrayList<Venue> getAllVenues() throws SQLException;
}
