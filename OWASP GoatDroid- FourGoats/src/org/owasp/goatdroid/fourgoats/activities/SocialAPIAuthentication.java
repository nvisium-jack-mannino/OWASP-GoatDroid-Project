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
 * @author Walter Tighzert
 * @created 2012
 */
package org.owasp.goatdroid.fourgoats.activities;

import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.login.LoginRequest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SocialAPIAuthentication extends Activity {

	EditText userNameEditText;
	EditText passwordEditText;
	Context context;
	boolean isAuthenticated;
	String sessionToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_api_authentication);
		userNameEditText = (EditText) findViewById(R.id.userName);
		passwordEditText = (EditText) findViewById(R.id.password);
		context = getApplicationContext();
		sessionToken = "";
	}

	public void doAuthenticateAPI(View v) {

		AuthenticateAsyncTask task = new AuthenticateAsyncTask();
		task.execute(null, null);
	}

	public boolean allFieldsCompleted(String userName, String password) {

		return (!userName.equals("") && !password.equals(""));
	}

	public void launchLogin() {
		Intent intent = new Intent(context, Login.class);
		startActivity(intent);
	}

	private class AuthenticateAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			LoginRequest client = new LoginRequest(context);
			String userName = userNameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			HashMap<String, String> userInfo = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			try {
				sessionToken = uidh.getSessionToken();

				if (allFieldsCompleted(userName, password)) {
					if (sessionToken.isEmpty() || sessionToken == null) {
						userInfo.put("errors", Constants.INVALID_SESSION);
						userInfo.put("success", "false");
					} else {
						userInfo = client.validateCredentialsAPI(userName,
								password);
					}
				} else {
					if (sessionToken != null) {
						isAuthenticated = true;
						userInfo.put("success", "true");
					} else {
						userInfo.put("success", "false");
						userInfo.put("errors", Constants.INVALID_SESSION);
					}
				}
			} catch (Exception e) {
				userInfo.put("errors", e.getMessage());
				userInfo.put("success", "false");
				Log.w("Failed login", "Login with " + userName + " " + password
						+ " failed");
			} finally {
				uidh.close();
			}
			return userInfo;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Utils.makeToast(context, Constants.SUCCESS_API_AUTH,
						Toast.LENGTH_LONG);
				sessionToken = results.get("sessionToken");
			} else if (results.get("errors").equals(Constants.INVALID_SESSION)) {
				Utils.makeToast(context, Constants.INVALID_SESSION,
						Toast.LENGTH_LONG);
				launchLogin();
			} else
				Utils.makeToast(getApplicationContext(), results.get("errors"),
						Toast.LENGTH_SHORT);

			Intent resultIntent = new Intent();
			resultIntent.putExtra("sessionToken", sessionToken);
			if (getParent() == null)
				setResult(Activity.RESULT_OK, resultIntent);
			else
				getParent().setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
	}
}
