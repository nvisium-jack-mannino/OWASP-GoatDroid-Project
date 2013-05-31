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
import org.owasp.goatdroid.fourgoats.db.CheckinDBHelper;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.addvenue.AddVenueRequest;
import org.owasp.goatdroid.fourgoats.rest.checkin.CheckinRequest;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddVenue extends BaseActivity {

	Context context;
	Bundle bundle;
	EditText venueNameText;
	EditText venueWebsiteText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_venue);
		context = this.getApplicationContext();
		bundle = getIntent().getExtras();
		venueNameText = (EditText) findViewById(R.id.venueName);
		venueWebsiteText = (EditText) findViewById(R.id.venueWebsite);
	}

	public void addVenue(View v) {

		if (!allFieldsCompleted(venueNameText.getText().toString(),
				venueWebsiteText.getText().toString())) {
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
		} else {
			AddVenueAsyncTask task = new AddVenueAsyncTask();
			task.execute(null, null);
		}
	}

	public boolean allFieldsCompleted(String venueName, String venueWebsite) {

		return (!venueName.equals("") && !venueWebsite.equals(""));
	}

	public void launchViewCheckin() {
		Intent intent = new Intent(context, ViewCheckin.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void launchAddVenue() {
		Intent intent = new Intent(context, AddVenue.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void launchLogin() {
		Intent intent = new Intent(context, Login.class);
		startActivity(intent);
	}

	private class AddVenueAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			UserInfoDBHelper dbHelper = new UserInfoDBHelper(context);
			CheckinDBHelper checkinDBHelper = new CheckinDBHelper(context);
			AddVenueRequest request = new AddVenueRequest(context);
			HashMap<String, String> addVenueInfo = new HashMap<String, String>();
			String sessionToken = "";

			try {
				sessionToken = dbHelper.getSessionToken();
				if (sessionToken.equals(""))
					addVenueInfo.put("errors", Constants.INVALID_SESSION);
				else {
					addVenueInfo = request.doAddVenue(sessionToken,
							venueNameText.getText().toString(),
							venueWebsiteText.getText().toString(),
							bundle.getString("latitude"),
							bundle.getString("longitude"));
					if (addVenueInfo.get("success").equals("true")) {
						CheckinRequest checkinRequest = new CheckinRequest(
								context);
						HashMap<String, String> checkinInfo = checkinRequest
								.doCheckin(sessionToken,
										bundle.getString("latitude"),
										bundle.getString("longitude"));

						if (checkinInfo.get("success").equals("true")) {
							checkinInfo.put("latitude",
									bundle.getString("latitude"));
							checkinInfo.put("longitude",
									bundle.getString("longitude"));
							checkinDBHelper.insertCheckin(checkinInfo);
							bundle.putString("checkinID",
									checkinInfo.get("checkinID"));
							bundle.putString("dateTime",
									checkinInfo.get("dateTime"));
						} else {
							addVenueInfo.put("success", "false");
							addVenueInfo.put("errors",
									checkinInfo.get("errors"));
						}
					}
				}
			} catch (Exception e) {
				addVenueInfo.put("errors", e.getMessage());
			} finally {
				dbHelper.close();
				checkinDBHelper.close();
			}
			return addVenueInfo;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				bundle.putString("venueName", venueNameText.getText()
						.toString());
				bundle.putString("venueWebsite", venueWebsiteText.getText()
						.toString());
				launchViewCheckin();
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
