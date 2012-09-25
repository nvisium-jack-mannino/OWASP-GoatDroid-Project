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
package org.owasp.goatdroid.webservice.herdfinancial;

import org.owasp.goatdroid.gui.Utils;

public class Constants {

	// Modify this to change the DB creds or DB location
	public static final String DB_CONNECTION_STRING = "jdbc:derby:"
			+ Utils.getCurrentPath() + "dbs/herdfinancial";

	public static final long MILLISECONDS_MONTH = 2419200000L; // 2,419,200,000
																// =
																// milliseconds
																// per 28 days
	public static final String UNEXPECTED_ERROR = "An unexpected error has occurred";
	public static final String SESSION_EXPIRED = "Session expired";
	public static final String INVALID_ACCOUNT_NUMBER = "Invalid account number";
	public static final String DEVICE_ALREADY_AUTHORIZED = "Device is already authorized";
	public static final String INVALID_DEVICE_ID = "Invalid device ID";
	public static final String ACCOUNT_NUMBER_ALREADY_REGISTERED = "The account number is already registered";
	public static final String USERNAME_ALREADY_REGISTERED = "Username already registered";
	public static final String INVALID_DATE_FORMAT = "Invalid date format";
	public static final String INVALID_CURRENCY_FORMAT = "Invalid currency format";
	public static final String INSUFFICIENT_FUNDS = "Insufficient funds";
	public static final String LULZ = "Nice try";
	public static final String SECRET_QUESTION_ANSWER_TOO_LONG = "Secret question answers must be less than 100 characters";
	public static final String USERNAME_FORMAT_INVALID = "Username can only contain numbers and letters, and must be less than 20 characters";
	public static final String SECRET_QUESTION_INDEX_INVALID = "Secret question index can only be 1-3";
	public static final String INVALID_SECRET_QUESTION_ANSWER = "Invalid secret question answer";
	public static final String INVALID_PASSWORD_RESET_CODE = "Invalid password reset code";
	public static final String INVALID_PASSWORD_LENGTH = "Password cannot be empty and must be less than 30 characters";
	public static final String SESSION_TOKEN = "AUTH";
	public static final String INVALID_CREDENTIALS = "Invalid credentials";
	public static final String INVALID_RECIPIENT = "The recipient does not exist";
}
