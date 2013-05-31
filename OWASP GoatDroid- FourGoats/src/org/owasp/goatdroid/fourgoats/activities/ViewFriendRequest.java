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
import org.owasp.goatdroid.fourgoats.rest.friends.FriendRequest;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewFriendRequest extends BaseActivity {

	Context context;
	Bundle bundle;
	TextView userName;
	TextView fullName;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_request);
		context = this.getApplicationContext();
		bundle = getIntent().getExtras();
		userName = (TextView) findViewById(R.id.userName);
		fullName = (TextView) findViewById(R.id.fullName);
		userName.setText("Username: " + bundle.getString("userName"));
		fullName.setText("Full Name: " + bundle.getString("fullName"));
	}

	public void acceptFriendRequest(View v) {

		AcceptRequestAsyncTask task = new AcceptRequestAsyncTask();
		task.execute(null, null);
	}

	public void denyFriendRequest(View V) {
		DenyRequestAsyncTask task = new DenyRequestAsyncTask();
		task.execute(null, null);
	}

	private class AcceptRequestAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> responseInfo = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			FriendRequest rest = new FriendRequest(context);

			try {
				String sessionToken = uidh.getSessionToken();
				if (sessionToken.equals("")) {
					responseInfo.put("errors", Constants.INVALID_SESSION);
					responseInfo.put("success", "false");
				} else {
					responseInfo = rest.acceptFriendRequest(sessionToken,
							bundle.getString("userName"));
				}
			} catch (Exception e) {
				responseInfo.put("errors", e.getMessage());
				responseInfo.put("success", "false");
			} finally {
				uidh.close();
			}
			return responseInfo;
		}

		public void onPostExecute(HashMap<String, String> results) {

			if (results.get("success").equals("true")) {
				Utils.makeToast(context,
						Constants.FRIEND_REQUEST_ACCEPTED_SENT,
						Toast.LENGTH_LONG);
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}

	private class DenyRequestAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> responseInfo = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			FriendRequest rest = new FriendRequest(context);

			try {
				String sessionToken = uidh.getSessionToken();
				if (sessionToken.equals("")) {
					responseInfo.put("errors", Constants.INVALID_SESSION);
				} else {
					responseInfo = rest.denyFriendRequest(sessionToken,
							bundle.getString("userName"));
				}
			} catch (Exception e) {
				responseInfo.put("errors", e.getMessage());
				responseInfo.put("success", "false");
			} finally {
				uidh.close();
			}
			return responseInfo;
		}

		public void onPostExecute(HashMap<String, String> results) {

			if (results.get("success").equals("true")) {
				Utils.makeToast(context,
						Constants.FRIEND_REQUEST_DECLINED_SENT,
						Toast.LENGTH_LONG);
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}

}
