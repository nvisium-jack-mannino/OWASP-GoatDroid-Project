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
import org.owasp.goatdroid.webservice.fourgoats.dao.FGHistoryDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.HistoryModel;
import org.springframework.stereotype.Service;

@Service
public class FGHistoryServiceImpl implements HistoryService {

	@Resource
	FGHistoryDaoImpl dao;

	public HistoryModel getHistory(String authToken) {

		HistoryModel history = new HistoryModel();
		ArrayList<String> errors = new ArrayList<String>();
		try {
			history.setHistory(dao.getCheckinHistory(dao.getUserID(authToken)));
			history.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			history.setErrors(errors);
		}
		return history;
	}

	/*
	 * All we pass into this is the username...hmm
	 */
	public HistoryModel getUserHistory(String username) {

		HistoryModel history = new HistoryModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			history.setHistory(dao.getCheckinHistoryByUserName(username));
			history.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			history.setErrors(errors);
		}
		return history;
	}
}
