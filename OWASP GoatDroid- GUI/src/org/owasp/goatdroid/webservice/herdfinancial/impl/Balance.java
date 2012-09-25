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
import java.util.HashMap;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.bean.BalanceBean;
import org.owasp.goatdroid.webservice.herdfinancial.dao.BalanceDAO;

public class Balance {

	static public BalanceBean getBalances(String accountNumber, int sessionToken) {

		BalanceBean bean = new BalanceBean();
		ArrayList<String> errors = new ArrayList<String>();
		BalanceDAO dao = new BalanceDAO();

		if (!Login.isSessionValid(sessionToken))
			errors.add(Constants.SESSION_EXPIRED);
		else if (!Validators.validateAccountNumber(accountNumber))
			errors.add(Constants.INVALID_ACCOUNT_NUMBER);

		try {
			if (errors.size() == 0) {
				dao.openConnection();
				HashMap<String, Double> balances = dao
						.getBalances(accountNumber);
				bean.setCheckingBalance(balances.get("checking"));
				bean.setSavingsBalance(balances.get("savings"));
				bean.setSuccess(true);
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
