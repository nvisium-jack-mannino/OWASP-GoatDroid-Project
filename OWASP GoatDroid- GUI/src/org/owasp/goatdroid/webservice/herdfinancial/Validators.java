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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

	public static final String ACCOUNT_NUMBER_PATTERN = "^[a-zA-Z0-9]{10}$";
	public static final String NAME_PATTERN = "^\\w{2,20}$";
	public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{2,20}$";
	public static final String DEVICE_ID_PATTERN = "^[a-zA-Z0-9]{10,20}$";
	public static final String COOKIE_PATTERN = "^\\d{8}$";
	public static final String DATE_TIME_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String MONEY_PATTERN = "\\d{0,20}.\\d{1,2}";
	public static final String SESSION_TOKEN_PATTERN = "^\\d{1,7}";
	public static final String PASSWORD_RESET_TOKEN_PATTERN = "^\\d{6,7}";
	public static final String SECRET_QUESTION_INDEX_PATTERN = "^[1-3]$";

	static public boolean isFormatValid(String input, String regexPattern) {

		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	static public boolean validateAccountNumber(String accountNumber) {

		return isFormatValid(accountNumber, ACCOUNT_NUMBER_PATTERN);
	}

	static public ArrayList<String> validateCredentials(String userName,
			String password) {

		ArrayList<String> errors = new ArrayList<String>();

		if (!isFormatValid(userName, Validators.USERNAME_PATTERN))
			errors.add("Username can only contain numbers and letters and must be 2-20 characters");
		if (password.length() == 0)
			errors.add("Password cannot be empty");
		return errors;
	}

	static public boolean validateDeviceID(String deviceID) {

		if (isFormatValid(deviceID, Validators.DEVICE_ID_PATTERN))
			return true;
		else
			return false;
	}

	static public ArrayList<String> validateRegistrationFields(
			String accountNumber, String firstName, String lastName,
			String userName, String password) {

		ArrayList<String> errors = new ArrayList<String>();

		if (!isFormatValid(accountNumber, Validators.ACCOUNT_NUMBER_PATTERN))
			errors.add(Constants.INVALID_ACCOUNT_NUMBER
					+ ". Must be 10 digits (i.e.- 1234567890)");
		if (!isFormatValid(firstName, Validators.NAME_PATTERN))
			errors.add("First name can only contain letters and must be 2-20 characters");
		if (!isFormatValid(lastName, Validators.NAME_PATTERN))
			errors.add("Last name can only contain letters and must be 2-20 characters");
		if (!isFormatValid(userName, Validators.USERNAME_PATTERN))
			errors.add("Username can only contain numbers and letters and must be 2-20 characters");
		if (password.length() == 0 || password.length() > 30)
			errors.add("Password cannot be empty and must be less than 30 characters");
		return errors;
	}

	static public boolean validateDateTimeFormat(String dateString) {

		return isFormatValid(dateString, Validators.DATE_TIME_PATTERN);
	}

	static public boolean validateAmountFormat(double amount) {

		return isFormatValid(Double.toString(amount), Validators.MONEY_PATTERN);
	}

	static public boolean validateSessionTokenFormat(int sessionToken) {
		return isFormatValid(Integer.toString(sessionToken),
				SESSION_TOKEN_PATTERN);
	}

	static public boolean validatePasswordResetTokenFormat(int resetToken) {
		return isFormatValid(Integer.toString(resetToken),
				PASSWORD_RESET_TOKEN_PATTERN);
	}

	static public boolean validateSecretQuestionAnswers(String answer1,
			String answer2, String answer3) {

		if (answer1.length() > 100 || answer2.length() > 100
				|| answer3.length() > 100)
			return false;
		else
			return true;

	}

	static public boolean validateUserNameFormat(String userName) {
		return isFormatValid(userName, USERNAME_PATTERN);
	}

	static public boolean validateSecretQuestionIndex(String secretQuestionIndex) {
		return isFormatValid((secretQuestionIndex),
				SECRET_QUESTION_INDEX_PATTERN);
	}

	static public boolean validateSingleSecretQuestionAnswer(String answer) {
		if (answer.length() > 100)
			return false;
		else
			return true;
	}

	static public boolean validatePasswordLength(String password) {
		return (!(password.length() == 0) && password.length() < 30);
	}
}
