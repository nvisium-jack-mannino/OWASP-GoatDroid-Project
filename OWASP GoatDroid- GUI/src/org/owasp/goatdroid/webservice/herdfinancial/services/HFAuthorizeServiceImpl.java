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
package org.owasp.goatdroid.webservice.herdfinancial.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.dao.HFAuthorizeDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.model.AuthorizeModel;
import org.springframework.stereotype.Service;

@Service
public class HFAuthorizeServiceImpl implements AuthorizeService {

	@Resource
	HFAuthorizeDaoImpl dao;

	public AuthorizeModel authorizeDevice(String deviceID, int sessionToken) {

		AuthorizeModel bean = new AuthorizeModel();
		ArrayList<String> errors = new ArrayList<String>();
		HFLoginServiceImpl loginService = new HFLoginServiceImpl();
		if (!Validators.validateDeviceID(deviceID))
			errors.add(Constants.INVALID_DEVICE_ID);

		try {
			if (!dao.isDeviceAuthorized(deviceID)) {
				dao.authorizeDevice(deviceID, sessionToken);
				bean.setSuccess(true);
			} else
				errors.add(Constants.DEVICE_ALREADY_AUTHORIZED);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}
}
