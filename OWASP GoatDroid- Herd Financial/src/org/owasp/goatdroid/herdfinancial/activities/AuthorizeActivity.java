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
import org.owasp.goatdroid.herdfinancial.request.AuthorizeRequest;
import org.owasp.goatdroid.herdfinancial.responseobjects.GenericResponseObject;
import org.owasp.goatdroid.herdfinancial.responseobjects.ResponseObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AuthorizeActivity extends BaseActivity {

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

		Intent intent = new Intent(AuthorizeActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	public void launchHome() {
		Intent intent = new Intent(context, HomeActivity.class);
		startActivity(intent);
	}

	private class AuthorizeAsyncTask extends
			AsyncTask<Void, Void, GenericResponseObject> {

		@Override
		protected GenericResponseObject doInBackground(Void... params) {

			AuthorizeRequest rest = new AuthorizeRequest(context);
			GenericResponseObject response = new GenericResponseObject();
			try {
				response = rest.authorizeDevice(Utils.getDeviceID(context));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(GenericResponseObject response) {
			if (response.isSuccess()) {
				Utils.makeToast(context, Constants.AUTHORIZE_SUCCESS,
						Toast.LENGTH_LONG);
				launchHome();
			} else
				Utils.makeToast(context,
						Utils.mergeArrayList(response.getErrors()),
						Toast.LENGTH_LONG);
		}
	}
}
