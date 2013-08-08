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

import java.sql.Date;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.dao.HFStatementDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.model.Statement;
import org.springframework.stereotype.Service;

@Service
public class HFStatementServiceImpl implements StatementService {

	@Resource
	HFStatementDaoImpl dao;

	public Statement getStatement(String accountNumber, String startDate,
			String endDate, String authToken) {

		ArrayList<String> errors = new ArrayList<String>();
		Statement statement = new Statement();
		if (!Validators.validateDateTimeFormat(startDate)
				|| !Validators.validateDateTimeFormat(endDate))
			errors.add(Constants.INVALID_ACCOUNT_NUMBER);

		else if (!Validators.validateAccountNumber(accountNumber))
			errors.add(Constants.INVALID_ACCOUNT_NUMBER);

		try {
			if (errors.size() == 0) {
				statement.setStatementData(dao.getStatement(accountNumber,
						convertStringToDate(startDate),
						convertStringToDate(endDate)));
				statement.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			statement.setErrors(errors);
			try {
			} catch (Exception e) {
			}
		}
		return statement;
	}

	public Statement getStatementSinceLastPoll(String accountNumber,
			String authToken) {

		ArrayList<String> errors = new ArrayList<String>();
		Statement statement = new Statement();
		if (!Validators.validateAccountNumber(accountNumber))
			errors.add(Constants.INVALID_ACCOUNT_NUMBER);

		try {
			if (errors.size() == 0) {
				long timeStamp = dao.getLastPollTime(accountNumber);
				statement.setStatementData(dao.getTransactionsSinceLastPoll(
						accountNumber, timeStamp));
				dao.updateLastPollTime(accountNumber);
				statement.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			statement.setErrors(errors);
		}
		return statement;
	}

	Date convertStringToDate(String dateString) {

		return Date.valueOf(dateString);
	}
}
