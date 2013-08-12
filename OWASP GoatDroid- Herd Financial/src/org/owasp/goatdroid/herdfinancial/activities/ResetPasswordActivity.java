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
import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.request.ForgotPasswordRequest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends BaseActivity {

	Context context;
	Bundle bundle;
	String password;
	String passwordConfirm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_password);
		bundle = getIntent().getExtras();
		context = getApplicationContext();
	}

	public void doResetPassword(View v) {

		password = ((EditText) findViewById(R.id.passwordEditText)).getText()
				.toString();
		passwordConfirm = ((EditText) findViewById(R.id.confirmPasswordEditText))
				.getText().toString();

		if (!password.equals(passwordConfirm)) {
			Utils.makeToast(context, Constants.PASSWORDS_DONT_MATCH,
					Toast.LENGTH_LONG);
		} else if (!allFieldsCompleted(password, passwordConfirm)) {
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
		} else {
			ResetPasswordAsyncTask task = new ResetPasswordAsyncTask();
			task.execute(null, null);
		}
	}

	public boolean allFieldsCompleted(String password, String passwordConfirm) {

		return (!password.equals("") && !passwordConfirm.equals(""));
	}

	public void launchLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	private class ResetPasswordAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			ForgotPasswordRequest rest = new ForgotPasswordRequest(context);
			HashMap<String, String> resetPasswordData = new HashMap<String, String>();

			try {
				resetPasswordData = rest.updatePassword(
						bundle.getString("userName"),
						bundle.getString("passwordResetCode"), password);
			} catch (Exception e) {
				resetPasswordData.put("errors", e.getMessage());
				resetPasswordData.put("success", "false");
			}
			return resetPasswordData;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Utils.makeToast(context, Constants.PASSWORD_RESET_SUCCESS,
						Toast.LENGTH_LONG);
				launchLogin();
			} else
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
		}
	}

}
