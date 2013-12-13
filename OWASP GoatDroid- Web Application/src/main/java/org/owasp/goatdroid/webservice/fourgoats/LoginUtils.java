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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.owasp.goatdroid.webservice.fourgoats.exception.Base64Exception;
import org.owasp.goatdroid.webservice.fourgoats.model.AuthorizationHeader;
import org.springframework.security.crypto.codec.Base64;

public class LoginUtils {

	static public long getTimeMilliseconds() {

		Calendar current = Calendar.getInstance();
		return current.getTimeInMillis();
	}

	static public String getCurrentDateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
		return df.format(cal.getTime());
	}

	static public String generateSaltedSHA512Hash(String input, String salt) {

		try {
			return generateSaltedSHAHash("SHA-512", input, salt);
		} catch (NoSuchAlgorithmException e) {
			return Constants.ALGORITHM_NOT_FOUND;
		}
	}

	static public String generateSaltedSHA256Hash(String input, String salt) {

		try {
			return generateSaltedSHAHash("SHA-256", input, salt);
		} catch (NoSuchAlgorithmException e) {
			return Constants.ALGORITHM_NOT_FOUND;
		}
	}

	static public String generateSaltedSHAHash(String algorithm, String input,
			String salt) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(salt.getBytes());
		byte byteData[] = md.digest(input.getBytes());
		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

	static public AuthorizationHeader decodeAuthorizationHeader(
			String encodedAuthorizationString) throws Base64Exception {
		/*
		 * First we check to ensure that the string is real Base64 and not
		 * garbage
		 */
		if (Base64.isBase64(encodedAuthorizationString.getBytes())) {
			String authorizationString = new String(
					Base64.decode(encodedAuthorizationString.getBytes()));

			/*
			 * We split the username and password on the first colon. Any colons
			 * encountered afterwards are considered part of the password.
			 */
			String username = "";
			String authToken = "";
			boolean isColonFound = false;
			for (char ch : authorizationString.toCharArray()) {
				if (ch == ':')
					isColonFound = true;
				else if (!isColonFound)
					username += ch;
				else
					authToken += ch;
			}
			AuthorizationHeader authHeader = new AuthorizationHeader();
			authHeader.setUsername(username);
			authHeader.setAuthToken(authToken);
			/*
			 * We check to make sure both the username and token were there
			 * after we decode and parse the Base64 string
			 */
			if (authHeader.getUsername().isEmpty()
					|| authHeader.getAuthToken().isEmpty())
				throw new Base64Exception(Constants.INVALID_BASE64);
			else
				return authHeader;
		} else
			throw new Base64Exception(Constants.INVALID_BASE64);
	}
}
