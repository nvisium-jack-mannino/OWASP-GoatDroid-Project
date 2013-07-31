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
import org.owasp.goatdroid.webservice.fourgoats.dao.FGAdminDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.AdminModel;
import org.owasp.goatdroid.webservice.fourgoats.model.BaseModel;
import org.owasp.goatdroid.webservice.fourgoats.model.GetUsersAdminModel;
import org.owasp.goatdroid.webservice.fourgoats.model.LoginModel;
import org.springframework.stereotype.Service;

@Service
public class FGAdminServiceImpl implements AdminService {

	@Resource
	FGAdminDaoImpl dao;

	public BaseModel deleteUser(String authToken, String userName) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			/*
			 * If the user has the admin role then we proceed
			 */
			if (dao.isAdmin(authToken)) {
				dao.deleteUser(userName);
				base.setSuccess(true);
			} else
				errors.add(Constants.NOT_AUTHORIZED);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}

	public BaseModel resetPassword(String authToken, String userName,
			String newPassword) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {

			/*
			 * If the user has the admin role then we proceed
			 */
			if (dao.isAdmin(authToken)) {
				dao.updatePassword(userName, newPassword);
				base.setSuccess(true);
			} else
				errors.add(Constants.NOT_AUTHORIZED);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}

	public GetUsersAdminModel getUsers(String authToken) {

		GetUsersAdminModel model = new GetUsersAdminModel();
		ArrayList<String> errors = new ArrayList<String>();
		try {
			/*
			 * If the user has the admin role then we proceed
			 */
			if (dao.isAdmin(authToken)) {
				model.setUsers(dao.getUsers());
				model.setSuccess(true);
			} else
				errors.add(Constants.NOT_AUTHORIZED);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			model.setErrors(errors);
		}
		return model;
	}

	public BaseModel signOut(String authToken) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			dao.terminateAuth(authToken);
			base.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}
}
