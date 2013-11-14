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

import java.util.HashMap;

import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.base.BaseActivity;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.request.BalancesRequest;
import org.owasp.goatdroid.herdfinancial.responseobjects.Balances;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GetBalanceActivity extends BaseActivity {

	Context context;
	TextView checkingBalanceTextView;
	TextView savingsBalanceTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_balance);
		context = this.getApplicationContext();
		checkingBalanceTextView = (TextView) findViewById(R.id.checkingBalanceTextView);
		savingsBalanceTextView = (TextView) findViewById(R.id.savingsBalanceTextView);
		GetMyBalance myRewards = new GetMyBalance();
		myRewards.execute(null, null);
	}

	public void launchLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	private class GetMyBalance extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			Balances balances = new Balances();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			BalancesRequest rest = new BalancesRequest(context);
			try {
				String sessionToken = uidh.getSessionToken();
				String accountNumber = uidh.getAccountNumber();
				if (sessionToken.equals("")) {
					balanceData.put("errors", Constants.INVALID_SESSION);
					balanceData.put("success", "false");
				} else {
					balanceData = rest.getMyBalance(accountNumber);
				}
			} catch (Exception e) {
				balanceData.put("errors", e.getMessage());
				balanceData.put("success", "false");
			} finally {
				uidh.close();
			}

			return balanceData;
		}

		public void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				checkingBalanceTextView.setText(results.get("checkingBalance"));
				savingsBalanceTextView.setText(results.get("savingsBalance"));
			} else if (results.get("errors").equals(Constants.INVALID_SESSION)) {
				Utils.makeToast(context, Constants.INVALID_SESSION,
						Toast.LENGTH_LONG);
				launchLogin();
			} else
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
		}
	}
}
