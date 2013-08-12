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

import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.request.RegisterRequest;
import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.base.BaseUnauthenticatedActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseUnauthenticatedActivity {

	Context context;
	String firstName;
	String lastName;
	String userName;
	String password;
	String passwordConfirm;
	String accountNumber;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		context = this.getApplicationContext();
	}

	public void doRegister(View v) {

		EditText firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
		EditText lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
		EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		EditText passwordConfirmEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
		EditText accountNumberEditText = (EditText) findViewById(R.id.accountNumberEditText);

		firstName = firstNameEditText.getText().toString();
		lastName = lastNameEditText.getText().toString();
		userName = userNameEditText.getText().toString();
		password = passwordEditText.getText().toString();
		passwordConfirm = passwordConfirmEditText.getText().toString();
		accountNumber = accountNumberEditText.getText().toString();

		if (!password.equals(passwordConfirm)) {
			Utils.makeToast(context, Constants.PASSWORDS_DONT_MATCH,
					Toast.LENGTH_LONG);
		} else if (!allFieldsCompleted(firstName, lastName, userName, password,
				passwordConfirm, accountNumber)) {
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
		} else {
			RegisterAsyncTask task = new RegisterAsyncTask();
			task.execute(null, null);
		}
	}

	public boolean allFieldsCompleted(String firstName, String lastName,
			String userName, String password, String passwordConfirm,
			String accountNumber) {

		return (!firstName.equals("") && !lastName.equals("")
				&& !accountNumber.equals("") && !userName.equals("")
				&& !password.equals("") && !passwordConfirm.equals(""));
	}

	public void launchLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	private class RegisterAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			RegisterRequest client = new RegisterRequest(context);
			HashMap<String, String> registerInfo = new HashMap<String, String>();

			try {
				registerInfo = client.validateRegistration(firstName, lastName,
						userName, password, accountNumber);
			} catch (Exception e) {
				registerInfo.put("errors", e.getMessage());
				registerInfo.put("success", "false");
			}
			return registerInfo;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Utils.makeToast(context, Constants.REGISTRATION_SUCCESS,
						Toast.LENGTH_LONG);
				launchLogin();
			} else
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
		}
	}
}
