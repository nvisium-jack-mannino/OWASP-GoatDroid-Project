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
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.bean.VenueListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.VenueBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.VenueDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueServiceImpl implements VenueService {

	VenueDaoImpl dao;

	@Autowired
	public VenueServiceImpl() {
		dao = new VenueDaoImpl();
	}

	public VenueBean addVenue(String sessionToken, String venueName,
			String venueWebsite, String latitude, String longitude) {

		VenueBean bean = new VenueBean();
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
					bean.setSuccess(true);
				} else
					errors.add(Constants.VENUE_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}

	public VenueListBean getAllVenues(String sessionToken) {

		VenueListBean bean = new VenueListBean();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			if (!dao.isSessionValid(sessionToken)
					|| Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				bean.setVenues(dao.getAllVenues());
				bean.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}
}
