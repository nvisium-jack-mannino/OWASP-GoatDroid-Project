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
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.dao.RegisterDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.bean.RegisterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

	RegisterDaoImpl dao;

	public RegisterServiceImpl() {
		dao = new RegisterDaoImpl();
	}

	public RegisterBean registerUser(String firstName, String lastName,
			String userName, String password) {

		RegisterBean bean = new RegisterBean();
		ArrayList<String> errors = Validators.validateRegistrationFields(
				firstName, lastName, userName, password);

		try {
			if (errors.size() == 0) {
				// if the user exists, we set an error and don't insert
				if (dao.doesUserExist(userName)) {
					errors.add(Constants.USERNAME_ALREADY_EXISTS);
				}
				// if the user doesn't exist, we insert
				else {
					dao.insertNewUser(firstName, lastName, userName, password);
					bean.setSuccess(true);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}
}
