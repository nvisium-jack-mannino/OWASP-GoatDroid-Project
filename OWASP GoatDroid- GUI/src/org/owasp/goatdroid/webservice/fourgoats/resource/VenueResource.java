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
package org.owasp.goatdroid.webservice.fourgoats.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.VenueListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.VenueBean;
import org.owasp.goatdroid.webservice.fourgoats.impl.Venue;

@Path("/fourgoats/api/v1/venues")
public class VenueResource {

	@Path("add")
	@POST
	@Produces("application/json")
	public VenueBean addVenue(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("venueName") String venueName,
			@FormParam("venueWebsite") String venueWebsite,
			@FormParam("latitude") String latitude,
			@FormParam("longitude") String longitude) {
		try {
			return Venue.addVenue(sessionToken, venueName, venueWebsite,
					latitude, longitude);
		} catch (NullPointerException e) {
			VenueBean bean = new VenueBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("list")
	@GET
	@Produces("application/json")
	public VenueListBean getAllVenues(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken) {
		try {
			return Venue.getAllVenues(sessionToken);
		} catch (NullPointerException e) {
			VenueListBean bean = new VenueListBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
