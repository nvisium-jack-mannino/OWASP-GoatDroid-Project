/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class VenueDAO extends BaseDAO {

	public VenueDAO() {
		super();
	}

	public boolean doesVenueExist(String venueName, String latitude,
			String longitude) throws SQLException {

		String sql = "select venueID from venues where venueName = ? or (latitude = ? "
				+ "and longitude = ?)";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, venueName);
		selectStatement.setString(2, latitude);
		selectStatement.setString(3, longitude);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public void insertNewVenue(String venueID, String venueName,
			String venueWebsite, String latitude, String longitude)
			throws SQLException {

		String sql = "insert into venues (venueID, venueName, venueWebsite, "
				+ "latitude, longitude) values (?,?,?,?,?)";
		PreparedStatement insertStatement = (PreparedStatement) conn
				.prepareCall(sql);
		insertStatement.setString(1, venueID);
		insertStatement.setString(2, venueName);
		insertStatement.setString(3, venueWebsite);
		insertStatement.setString(4, latitude);
		insertStatement.setString(5, longitude);
		insertStatement.executeUpdate();
	}

	public HashMap<String, String> getAllVenues() throws SQLException {

		String sql = "select venueID, venueName, venueWebsite, latitude, longitude from venues";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		ResultSet rs = selectStatement.executeQuery();
		HashMap<String, String> venues = new HashMap<String, String>();
		int count = 0;
		while (rs.next()) {
			venues.put("venueID" + count, rs.getString("venueID"));
			venues.put("venueName" + count, rs.getString("venueName"));
			venues.put("venueWebsite" + count, rs.getString("venueWebsite"));
			venues.put("latitude" + count, rs.getString("latitude"));
			venues.put("longitude" + count, rs.getString("longitude"));
			count++;
		}
		return venues;
	}
}
