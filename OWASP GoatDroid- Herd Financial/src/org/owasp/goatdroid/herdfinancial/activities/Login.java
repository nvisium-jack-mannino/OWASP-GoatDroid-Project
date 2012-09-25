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
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.rest.login.LoginRequest;
import org.owasp.goatdroid.herdfinancial.services.StatementUpdateService;
import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.base.BaseUnauthenticatedActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends BaseUnauthenticatedActivity {

	EditText userNameEditText;
	EditText passwordEditText;
	String userName;
	String password;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.login);
		userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
	}

	public void doLogin(View v) {

		userName = userNameEditText.getText().toString();
		password = passwordEditText.getText().toString();

		if (allFieldsCompleted(userName, password)) {
			LoginAsyncTask task = new LoginAsyncTask();
			task.execute(null, null);
		} else
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
	}

	public void launchRegisterActivity(View v) {

		Intent intent = new Intent(Login.this, Register.class);
		startActivity(intent);
	}

	public void launchForgotPasswordActivity(View v) {

		Intent intent = new Intent(Login.this, ForgotPassword.class);
		startActivity(intent);
	}

	public boolean allFieldsCompleted(String userName, String password) {

		return (!userName.equals("") && !password.equals(""));
	}

	public void launchHome() {
		Intent intent = new Intent(context, Home.class);
		startActivity(intent);
	}

	private class LoginAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			LoginRequest client = new LoginRequest(context);
			HashMap<String, String> userInfo = new HashMap<String, String>();
			UserInfoDBHelper dbHelper = new UserInfoDBHelper(context);

			try {
				userInfo = client.validateCredentials(userName, password);
				if (userInfo.get("success").equals("true")) {
					dbHelper.deleteInfo();
					dbHelper.insertSettings(userInfo);
				}
			} catch (Exception e) {
				userInfo.put("success", "false");
				userInfo.put("errors", e.getMessage());
				Log.w("Failed login", "Login with " + userName + " " + password
						+ " failed");
			} finally {
				dbHelper.close();
			}
			return userInfo;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Intent serviceIntent = new Intent(context,
						StatementUpdateService.class);
				startService(serviceIntent);
				launchHome();
			} else
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
		}
	}
}
