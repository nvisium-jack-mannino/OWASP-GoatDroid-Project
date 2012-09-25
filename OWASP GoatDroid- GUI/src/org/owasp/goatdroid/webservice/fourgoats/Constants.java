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

import org.owasp.goatdroid.gui.Utils;

public class Constants {

	// Modify this to change the DB creds or DB location
	public static final String DB_CONNECTION_STRING = "jdbc:derby:"
			+ Utils.getCurrentPath() + "dbs/fourgoats";

	public static final long SESSION_LIFETIME = 2419200000L; // 2,419,200,000
																// =
																// milliseconds
																// per 28 days
	public static final String SESSION_TOKEN_NAME = "SESS";
	public static final String INVALID_SESSION = "Invalid session";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FIRST_NAME_INVALID = "First name can only contain letters and must be less than 20 characters";
	public static final String LAST_NAME_INVALID = "Last name can only contain letters and must be less than 20 characters";
	public static final String USERNAME_FORMAT_INVALID = "Username can only contain numbers and letters, and must be less than 20 characters";
	public static final String PASSWORD_FORMAT_INVALID = "Password must be greater than 0 characters and less than 30";
	public static final String UNEXPECTED_ERROR = "An unexpected error has occurred";
	public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
	public static final String ALGORITHM_NOT_FOUND = "Algorithm not found";
	public static final String LOGIN_CREDENTIALS_INVALID = "Username or password were invalid";
	public static final String VENUE_DOESNT_EXIST = "Venue does not exist";
	public static final String NOT_AUTHORIZED = "Not authorized";
	public static final String ALREADY_FRIENDS = "You are already friends";
	public static final String NAME_TOO_LONG = "Name is too long";
	public static final String WEBSITE_LENGTH_TOO_LONG = "Website is too long";
	public static final String LATITUDE_FORMAT_INVALID = "Latitude format is invalid";
	public static final String LONGITUDE_FORMAT_INVALID = "Longitude format is invalid";
	public static final String VENUE_ALREADY_EXISTS = "Venue already exists";
	public static final String FRIEND_ALREADY_REQUESTED = "Friend already requested";
	public static final String CANNOT_DO_TO_YOURSELF = "Cannot perform that action for yourself";
	public static final String WEIRDNESS_HAPPENED = "Weirdness happened.";
}
