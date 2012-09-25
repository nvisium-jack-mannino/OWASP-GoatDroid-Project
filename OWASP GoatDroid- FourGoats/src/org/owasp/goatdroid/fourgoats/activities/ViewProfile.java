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
import org.owasp.goatdroid.fourgoats.base.BaseActivity;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.friends.FriendRequest;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProfile extends BaseActivity {

	private Context context;
	private Bundle bundle;
	private TextView userNameTextView;
	private TextView nameTextView;
	private TextView lastCheckinTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		context = this.getApplicationContext();
		bundle = getIntent().getExtras();
		userNameTextView = (TextView) findViewById(R.id.userNameTextView);
		nameTextView = (TextView) findViewById(R.id.nameTextView);
		lastCheckinTextView = (TextView) findViewById(R.id.lastCheckinInfo);
		GetProfileInfo getInfo = new GetProfileInfo();
		getInfo.execute(null, null);
	}

	public void requestAsFriend(View v) {

		RequestFriendAsyncTask task = new RequestFriendAsyncTask();
		task.execute(null, null);
	}

	public void viewUserCheckinHistory(View v) {
		Intent intent = new Intent(context, History.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private class GetProfileInfo extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> profileInfo = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			uidh.close();
			FriendRequest rest = new FriendRequest(context);
			try {
				if (sessionToken.equals("")) {
					profileInfo.put("errors", Constants.INVALID_SESSION);
					profileInfo.put("success", "false");
				} else {
					profileInfo = rest.getProfile(sessionToken,
							bundle.getString("userName"));
					if (profileInfo.get("success").equals("true")) {
						return profileInfo;

					} else {
						return profileInfo;
					}
				}
			} catch (Exception e) {
				profileInfo.put("errors", e.getMessage());
				profileInfo.put("success", "false");
			} finally {
				uidh.close();
			}
			return profileInfo;
		}

		public void onPostExecute(HashMap<String, String> users) {

			if (users.get("success").equals("true")) {
				userNameTextView.setText(bundle.getString("userName"));
				nameTextView.setText(users.get("firstName") + " "
						+ users.get("lastName"));
				if (users.get("lastCheckinTime").equals(""))
					lastCheckinTextView.setText("User has never checked in");
				else {
					String[] dateTime = users.get("lastCheckinTime").split(" ");
					lastCheckinTextView.setText("Date: " + dateTime[0]
							+ "\nTime: " + dateTime[1] + "\nLatitude: "
							+ users.get("lastLatitude") + "\nLongitude: "
							+ users.get("lastLongitude"));
				}
			} else {
				Utils.makeToast(context, Constants.WEIRD_ERROR,
						Toast.LENGTH_LONG);
			}
		}
	}

	private class RequestFriendAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			FriendRequest rest = new FriendRequest(context);
			HashMap<String, String> resultInfo = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			try {
				String sessionToken = uidh.getSessionToken();
				resultInfo = rest.doFriendRequest(sessionToken,
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
				Utils.makeToast(context, Constants.FRIEND_REQUEST_SENT,
						Toast.LENGTH_LONG);
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}
}
