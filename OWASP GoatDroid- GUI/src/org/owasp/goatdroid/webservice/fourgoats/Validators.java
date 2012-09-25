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
package org.owasp.goatdroid.webservice.fourgoats;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

	public static final String SESSION_TOKEN_FORMAT = "^[a-zA-Z0-9]{128}$";
	public static final String ID_FORMAT = "^[a-zA-Z0-9]{64}$";
	public static final String LAT_LONG_FORMAT = "\\d{1,3}.\\d{5,20}";
	public static final String LAT_LONG_FORMAT_WITH_NEGATIVE = "-\\d{1,3}.\\d{5,20}";
	public static final String DATE_TIME_FORMAT = "[0-9]{4}-[0-9]{2}-[0-9]{2} "
			+ "[0-9]{2}:[0-9]{2}:[0-9]{2}";
	public static final String FIRST_LAST_NAME_FORMAT = "[a-zA-Z]{1,20}";
	public static final String USERNAME_FORMAT = "^[a-zA-Z0-9]{1,20}$";
	public static final String VENUE_ID_FORMAT = "[a-zA-Z0-9]{0,64}";

	static public boolean isFormatValid(String input, String regexPattern) {

		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	static public boolean validateSessionTokenFormat(String sessionToken) {
		return isFormatValid(sessionToken, SESSION_TOKEN_FORMAT);
	}

	static public boolean validateIDFormat(String id) {
		return isFormatValid(id, ID_FORMAT);
	}

	static public boolean validateLatLong(String coord) {
		return isFormatValid(coord, LAT_LONG_FORMAT);
	}

	static public boolean validateDescriptionOrCommentLength(String comment) {
		return comment.length() <= 140;
	}

	static public boolean validateDateTime(String dateTime) {
		return isFormatValid(dateTime, DATE_TIME_FORMAT);
	}

	static public boolean validateItemNameLength(String name) {
		return name.length() <= 30;
	}

	static public boolean validateWebsiteLength(String website) {
		return website.length() <= 120;
	}

	static public boolean validateFirstLastName(String name) {
		return isFormatValid(name, FIRST_LAST_NAME_FORMAT);
	}

	static public boolean validatePasswordLength(String password) {
		return password.length() > 0 && password.length() <= 30;
	}

	static public boolean validateUserNameFormat(String userName) {
		return isFormatValid(userName, USERNAME_FORMAT);
	}

	static public ArrayList<String> validateRegistrationFields(
			String firstName, String lastName, String userName, String password) {

		ArrayList<String> errors = new ArrayList<String>();
		if (!isFormatValid(firstName, FIRST_LAST_NAME_FORMAT))
			errors.add(Constants.FIRST_NAME_INVALID);
		if (!isFormatValid(lastName, FIRST_LAST_NAME_FORMAT))
			errors.add(Constants.LAST_NAME_INVALID);
		if (!isFormatValid(userName, USERNAME_FORMAT))
			errors.add(Constants.USERNAME_FORMAT_INVALID);
		if (!validatePasswordLength(password))
			errors.add(Constants.PASSWORD_FORMAT_INVALID);
		return errors;
	}

	static public boolean validateCredentials(String userName, String password) {
		return validateUserNameFormat(userName)
				&& validatePasswordLength(password);
	}

	static public boolean validateCheckinFields(String latitude,
			String longitude) {

		return ((isFormatValid(latitude, LAT_LONG_FORMAT) || isFormatValid(
				latitude, LAT_LONG_FORMAT_WITH_NEGATIVE)
				&& (isFormatValid(longitude, LAT_LONG_FORMAT) || isFormatValid(
						longitude, LAT_LONG_FORMAT_WITH_NEGATIVE))));
	}

	static public boolean validateCommentFields(String comment, String checkinID) {
		return validateDescriptionOrCommentLength(comment)
				&& isFormatValid(checkinID, ID_FORMAT);
	}

	static public ArrayList<String> validateAddVenueValues(String venueName,
			String venueWebsite, String latitude, String longitude) {

		ArrayList<String> errors = new ArrayList<String>();
		if (!validateItemNameLength(venueName))
			errors.add(Constants.NAME_TOO_LONG);
		if (!validateWebsiteLength(venueWebsite))
			errors.add(Constants.WEBSITE_LENGTH_TOO_LONG);
		if (!(validateLatLong(latitude) || isFormatValid(latitude,
				LAT_LONG_FORMAT_WITH_NEGATIVE)))
			errors.add(Constants.LATITUDE_FORMAT_INVALID);
		if (!(validateLatLong(longitude) || isFormatValid(longitude,
				LAT_LONG_FORMAT_WITH_NEGATIVE)))
			errors.add(Constants.LONGITUDE_FORMAT_INVALID);
		return errors;
	}

	static public boolean validateFriendRequestAction(String action) {
		if (action.equals("deny") || action.equals("accept"))
			return true;
		else
			return false;
	}
}
