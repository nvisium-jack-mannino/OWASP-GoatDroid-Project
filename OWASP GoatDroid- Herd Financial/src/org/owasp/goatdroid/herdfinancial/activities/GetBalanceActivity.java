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

	private class GetMyBalance extends AsyncTask<Void, Void, Balances> {
		protected Balances doInBackground(Void... params) {

			Balances balances = new Balances();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			BalancesRequest rest = new BalancesRequest(context);
			try {
				String accountNumber = uidh.getAccountNumber();
				balances = rest.getMyBalance(accountNumber);
			} catch (Exception e) {
				balances.getErrors().add(e.getMessage());
			}

			return balances;
		}

		public void onPostExecute(Balances response) {
			if (response.isSuccess()) {
				checkingBalanceTextView.setText(response.getCheckingBalance());
				savingsBalanceTextView.setText(response.getSavingsBalance());
			} else if (Utils.mergeArrayList(response.getErrors()).contains(
					Constants.INVALID_SESSION)) {
				Utils.makeToast(context, Constants.INVALID_SESSION,
						Toast.LENGTH_LONG);
				launchLogin();
			} else
				Utils.makeToast(context,
						Utils.mergeArrayList(response.getErrors()),
						Toast.LENGTH_LONG);
		}
	}
}
