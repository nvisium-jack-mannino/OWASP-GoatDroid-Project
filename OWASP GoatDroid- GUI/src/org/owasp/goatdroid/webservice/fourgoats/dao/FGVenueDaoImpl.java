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

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGVenueDaoImpl extends BaseDaoImpl implements VenueDao {

	@Autowired
	public FGVenueDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean doesVenueExist(String venueName, String latitude,
			String longitude) throws SQLException {

		String sql = "SELECT venueID FROM app.fg_venues WHERE venueName = ? OR (latitude = ? "
				+ "AND longitude = ?)";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { venueName, latitude, longitude });
		if (rs.next())
			return true;
		else
			return false;
	}

	public void insertNewVenue(String venueID, String venueName,
			String venueWebsite, String latitude, String longitude)
			throws SQLException {

		String sql = "INSERT INTO app.fg_venues (venueID, venueName, venueWebsite, "
				+ "latitude, longitude) VALUES (?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { venueID, venueName, venueWebsite, latitude,
						longitude });
	}

	public HashMap<String, String> getAllVenues() throws SQLException {

		String sql = "SELECT venueID, venueName, venueWebsite, latitude, longitude FROM app.fg_venues";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql);
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
