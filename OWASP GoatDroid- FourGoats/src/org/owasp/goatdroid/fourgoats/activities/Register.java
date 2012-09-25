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
package org.owasp.goatdroid.fourgoats.activities;

import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.base.BaseUnauthenticatedActivity;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.register.RegisterRequest;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends BaseUnauthenticatedActivity {

	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		context = this.getApplicationContext();
	}

	public void submitRegistration(View v) {

		RegisterAsyncTask register = new RegisterAsyncTask(this);
		register.execute(null, null);
	}

	public void launchLogin() {
		Intent intent = new Intent(Register.this, Login.class);
		startActivity(intent);
	}

	public boolean allFieldsCompleted(String firstName, String lastName,
			String userName, String password) {

		return (!firstName.equals("") && !lastName.equals("")
				&& !userName.equals("") && !password.equals(""));
	}

	private class RegisterAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		Register mActivity;

		public RegisterAsyncTask(Register activity) {
			mActivity = activity;
		}

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			RegisterRequest client = new RegisterRequest(context);
			EditText firstNameEditText = (EditText) findViewById(R.id.firstNameEdit);
			EditText lastNameEditText = (EditText) findViewById(R.id.lastNameEdit);
			EditText userNameEditText = (EditText) findViewById(R.id.userNameEdit);
			EditText passwordEditText = (EditText) findViewById(R.id.passwordEdit);
			EditText passwordConfirmEditText = (EditText) findViewById(R.id.confirmPasswordEdit);
			String firstName = firstNameEditText.getText().toString();
			String lastName = lastNameEditText.getText().toString();
			String userName = userNameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			String passwordConfirm = passwordConfirmEditText.getText()
					.toString();
			HashMap<String, String> registerInfo = new HashMap<String, String>();

			try {
				if (password.equals(passwordConfirm)) {
					if (allFieldsCompleted(firstName, lastName, userName,
							password))
						registerInfo = client.validateRegistration(firstName,
								lastName, userName, password);
					else {
						registerInfo.put("errors",
								Constants.ALL_FIELDS_REQUIRED);
						registerInfo.put("success", "false");
					}
				} else {
					registerInfo.put("errors", Constants.PASSWORDS_DONT_MATCH);
					registerInfo.put("success", "false");
				}
			} catch (Exception e) {
				registerInfo.put("errors", Constants.COULD_NOT_CONNECT);
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
				Utils.makeToast(mActivity, results.get("errors"),
						Toast.LENGTH_LONG);
		}
	}
}
