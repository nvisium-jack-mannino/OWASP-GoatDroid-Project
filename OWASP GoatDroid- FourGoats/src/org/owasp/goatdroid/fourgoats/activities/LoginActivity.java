/**
e * OWASP GoatDroid Project
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
 * @author Walter Tighzert
 * @created 2012
 */
package org.owasp.goatdroid.fourgoats.activities;

import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.base.BaseUnauthenticatedActivity;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.LoginRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.Login;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseUnauthenticatedActivity {

	Context context;
	EditText userNameEditText;
	EditText passwordEditText;
	CheckBox rememberMeCheckBox;
	String previousActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		setContentView(R.layout.login);
		userNameEditText = (EditText) findViewById(R.id.userName);
		passwordEditText = (EditText) findViewById(R.id.password);
		rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);
		SharedPreferences prefs = getSharedPreferences("userinfo",
				MODE_WORLD_READABLE);
		try {
			previousActivity = getIntent().getExtras().getString(
					"previousActivity");
		} catch (NullPointerException e) {
			previousActivity = "";
		}
		userNameEditText.setText(prefs.getString("username", ""));
		passwordEditText.setText(prefs.getString("password", ""));
		if (prefs.getBoolean("remember", true))
			rememberMeCheckBox.setChecked(true);
		else
			rememberMeCheckBox.setChecked(false);
	}

	public void checkCredentials(View v) {

		if (allFieldsCompleted(userNameEditText.getText().toString(),
				passwordEditText.getText().toString())) {
			ValidateCredsAsyncTask validate = new ValidateCredsAsyncTask(this);
			validate.execute(null, null);
		} else
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);

	}

	public void launchRegistration(View v) {
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}

	public void launchHome() {
		Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	public void launchAdminHome() {
		Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
		startActivity(intent);
	}

	public boolean allFieldsCompleted(String userName, String password) {

		return (!userName.equals("") && !password.equals(""));
	}

	private class ValidateCredsAsyncTask extends AsyncTask<Void, Void, Login> {

		LoginActivity mActivity;

		public ValidateCredsAsyncTask(LoginActivity activity) {
			mActivity = activity;
		}

		@Override
		protected Login doInBackground(Void... params) {

			LoginRequest client = new LoginRequest(context);
			String username = userNameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			boolean rememberMe = rememberMeCheckBox.isChecked();
			Login login = new Login();
			if (allFieldsCompleted(username, password)) {
				try {
					login = client.validateCredentials(username, password);
					if (rememberMe)
						Utils.saveCredentials(mActivity, username, password);
				} catch (Exception e) {
					e.getMessage();
				}
			}
			return login;

		}

		protected void onPostExecute(Login login) {

			if (login.isSuccess()) {
				Utils.setInfo(context, login.getUsername(),
						login.getAuthToken(), login.getPreferences());
				if (Boolean.parseBoolean(login.getPreferences().get("isAdmin"))
						|| (userNameEditText.getText().toString()
								.equals("customerservice") && passwordEditText
								.getText().toString()
								.equals("Acc0uNTM@n@g3mEnT"))) {
					Intent intent = new Intent(mActivity,
							AdminHomeActivity.class);
					startActivity(intent);

				} else {
					Intent intent = new Intent(mActivity, HomeActivity.class);
					startActivity(intent);
				}
			} else {
				if (login.getErrors().isEmpty())
					Utils.makeToast(context, Constants.COULD_NOT_CONNECT,
							Toast.LENGTH_LONG);
				else
					Utils.makeToast(context,
							Utils.mergeArrayList(login.getErrors()),
							Toast.LENGTH_LONG);
			}

		}
	}
}
