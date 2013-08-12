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
package org.owasp.goatdroid.herdfinancial.activities;

import java.util.ArrayList;
import java.util.HashMap;

import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.base.BaseActivity;
import org.owasp.goatdroid.herdfinancial.db.StatementDBHelper;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.request.StatementsRequest;
import org.owasp.goatdroid.herdfinancial.request.TransferRequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TransferActivity extends BaseActivity {

	String toAccount;
	String fromAccount;
	String amount;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transfer);
		context = getApplicationContext();
		UserInfoDBHelper uidh = new UserInfoDBHelper(context);
		((EditText) findViewById(R.id.fromAccountEditText)).setText(uidh
				.getAccountNumber());
	}

	public void doTransfer(View v) {

		toAccount = ((EditText) findViewById(R.id.toAccountEditText)).getText()
				.toString();
		fromAccount = ((EditText) findViewById(R.id.fromAccountEditText))
				.getText().toString();
		amount = ((EditText) findViewById(R.id.amountEditText)).getText()
				.toString();

		TransferRequest client = new TransferRequest(context);
		HashMap<String, String> transferInfo;
		UserInfoDBHelper uidh = new UserInfoDBHelper(context);
		String sessionToken = uidh.getSessionToken();
		uidh.close();

		if (allFieldsCompleted(toAccount, fromAccount, amount)) {
			try {
				transferInfo = client.doTransfer(sessionToken, fromAccount,
						toAccount, amount);
				if (transferInfo.get("success").equals("true")) {
					Utils.makeToast(context, Constants.TRANSFER_SUCCESS,
							Toast.LENGTH_LONG);
					pollStatements();
					Intent intent = new Intent(TransferActivity.this, HomeActivity.class);
					startActivity(intent);
				} else
					Utils.makeToast(context, transferInfo.get("errors"),
							Toast.LENGTH_LONG);
			} catch (Exception e) {
				Utils.makeToast(context, Constants.WEIRD_ERROR,
						Toast.LENGTH_LONG);
			}
		}
	}

	public boolean allFieldsCompleted(String toAccount, String fromAccount,
			String amount) {

		return (!toAccount.isEmpty() && !fromAccount.isEmpty() && !amount
				.isEmpty());
	}

	public void pollStatements() {
		StatementDBHelper sdbh = new StatementDBHelper(context);
		UserInfoDBHelper uidh = new UserInfoDBHelper(context);
		StatementsRequest request = new StatementsRequest(context);
		try {
			ArrayList<HashMap<String, String>> transactions = request
					.getStatementUpdate(uidh.getSessionToken(),
							uidh.getAccountNumber());
			String userName = uidh.getUserName();
			for (HashMap<String, String> transaction : transactions) {
				transaction.put("userName", userName);
				sdbh.insert(transaction);
			}
		} catch (Exception e) {
			e.getMessage();

		} finally {
			uidh.close();
			sdbh.close();
		}
	}
}
