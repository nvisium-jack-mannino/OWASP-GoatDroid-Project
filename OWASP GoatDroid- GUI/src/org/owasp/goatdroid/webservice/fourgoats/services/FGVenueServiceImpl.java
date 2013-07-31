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
package org.owasp.goatdroid.webservice.fourgoats.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.dao.FGVenueDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.VenueListModel;
import org.springframework.stereotype.Service;

@Service
public class FGVenueServiceImpl implements VenueService {

	@Resource
	FGVenueDaoImpl dao;

	public BaseModel addVenue(String authToken, String venueName,
			String venueWebsite, String latitude, String longitude) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			errors = Validators.validateAddVenueValues(venueName, venueWebsite,
					latitude, longitude);

			if (errors.size() == 0) {
				if (!dao.doesVenueExist(venueName, latitude, longitude)) {
					String venueID = LoginUtils.generateSaltedSHA256Hash(
							venueName, Salts.VENUE_ID_GENERATOR_SALT);
					dao.insertNewVenue(venueID, venueName, venueWebsite,
							latitude, longitude);
					base.setSuccess(true);
				} else
					errors.add(Constants.VENUE_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}

	public VenueListModel getAllVenues(String authToken) {

		VenueListModel venueList = new VenueListModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			venueList.setVenues(dao.getAllVenues());
			venueList.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			venueList.setErrors(errors);
		}
		return venueList;
	}
}
