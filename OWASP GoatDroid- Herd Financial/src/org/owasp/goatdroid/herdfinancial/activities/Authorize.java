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
import org.owasp.goatdroid.herdfinancial.rest.authorize.AuthorizeRequest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Authorize extends BaseActivity {

	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authorize);
		context = getApplicationContext();
	}

	public void authorizeDevice(View v) {

		AuthorizeAsyncTask task = new AuthorizeAsyncTask();
		task.execute(null, null);
	}

	public void dontAuthorizeDevice(View v) {

		Intent intent = new Intent(Authorize.this, Home.class);
		startActivity(intent);
	}

	public void launchHome() {
		Intent intent = new Intent(context, Home.class);
		startActivity(intent);
	}

	private class AuthorizeAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			HashMap<String, String> authorizeData = new HashMap<String, String>();
			AuthorizeRequest rest = new AuthorizeRequest(context);

			try {
				String sessionToken = uidh.getSessionToken();
				if (sessionToken.isEmpty()) {
					authorizeData.put("errors", Constants.INVALID_SESSION);
					authorizeData.put("success", "false");
				} else {
					authorizeData = rest.authorizeDevice(sessionToken,
							Utils.getDeviceID(context));
				}
			} catch (Exception e) {
				authorizeData.put("success", "false");
				authorizeData.put("errors", e.getMessage());
			} finally {
				uidh.close();
			}
			return authorizeData;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Utils.makeToast(context, Constants.AUTHORIZE_SUCCESS,
						Toast.LENGTH_LONG);
				launchHome();
			} else
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
		}
	}
}
