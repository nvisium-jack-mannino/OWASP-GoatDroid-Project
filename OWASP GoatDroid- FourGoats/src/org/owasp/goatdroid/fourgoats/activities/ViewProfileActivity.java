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

import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.base.BaseActivity;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.FriendRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.Friend;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProfileActivity extends BaseActivity {

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
		Intent intent = new Intent(context, HistoryActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private class GetProfileInfo extends AsyncTask<Void, Void, Friend> {
		protected Friend doInBackground(Void... params) {

			Friend friend = new Friend();
			FriendRequest rest = new FriendRequest(context);

			try {
				friend = rest.getProfile(bundle.getString("userName"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return friend;
		}

		public void onPostExecute(Friend result) {

			if (result.isSuccess()) {
				userNameTextView.setText(bundle.getString("userName"));
				nameTextView.setText(result.getProfile() + " "
						+ result.getProfile().get("userName"));
				if (result.getProfile().get("lastCheckinTime").equals(""))
					lastCheckinTextView.setText("User has never checked in");
				else {
					String[] dateTime = result.getProfile()
							.get("lastCheckinTime").split(" ");
					lastCheckinTextView.setText("Date: " + dateTime[0]
							+ "\nTime: " + dateTime[1] + "\nLatitude: "
							+ result.getProfile().get("lastLatitude")
							+ "\nLongitude: "
							+ result.getProfile().get("lastLongitude"));
				}
			} else {
				Utils.makeToast(context, Constants.WEIRD_ERROR,
						Toast.LENGTH_LONG);
			}
		}
	}

	private class RequestFriendAsyncTask extends
			AsyncTask<Void, Void, GenericResponseObject> {
		protected GenericResponseObject doInBackground(Void... params) {

			FriendRequest rest = new FriendRequest(context);
			GenericResponseObject response = new GenericResponseObject();

			try {
				response = rest.doFriendRequest(bundle.getString("userName"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response;
		}

		public void onPostExecute(GenericResponseObject response) {

			if (response.isSuccess()) {
				Utils.makeToast(context, Constants.FRIEND_REQUEST_SENT,
						Toast.LENGTH_LONG);
			} else {
				Utils.makeToast(context,
						Utils.mergeArrayList(response.getErrors()),
						Toast.LENGTH_LONG);
			}
		}
	}
}
