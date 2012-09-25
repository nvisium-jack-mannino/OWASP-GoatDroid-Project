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
package org.owasp.goatdroid.webservice.herdfinancial.impl;

import java.util.ArrayList;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.bean.RegisterBean;
import org.owasp.goatdroid.webservice.herdfinancial.dao.RegisterDAO;

public class Register {

	static public RegisterBean registerUser(String accountNumber,
			String firstName, String lastName, String userName, String password) {

		RegisterBean bean = new RegisterBean();
		ArrayList<String> errors = Validators.validateRegistrationFields(
				accountNumber, firstName, lastName, userName, password);
		RegisterDAO dao = new RegisterDAO();

		try {
			if (errors.size() == 0) {
				dao.openConnection();
				if (!dao.doesUserNameExist(userName)) {
					if (!dao.doesAccountNumberExist(accountNumber)) {
						dao.registerUser(accountNumber, firstName, lastName,
								userName, password);
						bean.setSuccess(true);
					} else
						errors.add(Constants.ACCOUNT_NUMBER_ALREADY_REGISTERED);
				} else
					errors.add(Constants.USERNAME_ALREADY_REGISTERED);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}
}
