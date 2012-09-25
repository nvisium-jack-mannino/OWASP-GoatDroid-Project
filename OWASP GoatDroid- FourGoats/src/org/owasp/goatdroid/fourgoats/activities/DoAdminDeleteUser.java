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
import org.owasp.goatdroid.fourgoats.base.BaseActivity;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.admin.AdminRequest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DoAdminDeleteUser extends BaseActivity {

	Context context;
	Bundle bundle;
	TextView userNameTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_delete_user);
		context = this.getApplicationContext();
		bundle = getIntent().getExtras();
		userNameTextView = (TextView) findViewById(R.id.userNameTextView);
		userNameTextView.setText(bundle.getString("userName"));
	}

	public void doDeleteUser(View v) {

		DeleteUserAsyncTask task = new DeleteUserAsyncTask();
		task.execute(null, null);
	}

	public void launchAdminHome() {
		Intent intent = new Intent(DoAdminDeleteUser.this, AdminHome.class);
		startActivity(intent);
	}

	public void launchAdminHome(View v) {
		Intent intent = new Intent(DoAdminDeleteUser.this, AdminHome.class);
		startActivity(intent);
	}

	public void launchHome() {
		Intent intent = new Intent(context, AdminHome.class);
		startActivity(intent);
	}

	public void launchLogin() {
		Intent intent = new Intent(context, Login.class);
		startActivity(intent);
	}

	private class DeleteUserAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> resultInfo = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			AdminRequest rest = new AdminRequest(context);

			try {
				String sessionToken = uidh.getSessionToken();
				if (sessionToken.equals("")) {
					resultInfo.put("errors", Constants.INVALID_SESSION);
					resultInfo.put("success", "false");
				} else
					resultInfo = rest.deleteUser(sessionToken,
							bundle.getString("userName"));

			} catch (Exception e) {
				resultInfo.put("errors", e.getMessage());
				resultInfo.put("success", "false");
			} finally {
				uidh.close();
			}
			return resultInfo;
		}

		public void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Utils.makeToast(context, Constants.DELETION_SUCCESS,
						Toast.LENGTH_LONG);
				launchAdminHome();
			} else if (results.get("errors").equals(Constants.INVALID_SESSION)) {
				Utils.makeToast(context, Constants.INVALID_SESSION,
						Toast.LENGTH_LONG);
				launchLogin();
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}

}
