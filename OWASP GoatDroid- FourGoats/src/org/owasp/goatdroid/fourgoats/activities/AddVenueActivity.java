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
import org.owasp.goatdroid.fourgoats.db.CheckinDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.AddVenueRequest;
import org.owasp.goatdroid.fourgoats.request.CheckinRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.Checkin;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddVenueActivity extends BaseActivity {

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
		Intent intent = new Intent(context, ViewCheckinActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void launchAddVenue() {
		Intent intent = new Intent(context, AddVenueActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void launchLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	private class AddVenueAsyncTask extends
			AsyncTask<Void, Void, GenericResponseObject> {

		@Override
		protected GenericResponseObject doInBackground(Void... params) {

			CheckinDBHelper checkinDBHelper = new CheckinDBHelper(context);
			AddVenueRequest request = new AddVenueRequest(context);
			GenericResponseObject venue = new GenericResponseObject();

			try {

				venue = request.doAddVenue(venueNameText.getText().toString(),
						venueWebsiteText.getText().toString(),
						bundle.getString("latitude"),
						bundle.getString("longitude"));
				if (venue.isSuccess()) {
					CheckinRequest checkinRequest = new CheckinRequest(context);
					Checkin checkin = checkinRequest.doCheckin(
							bundle.getString("latitude"),
							bundle.getString("longitude"));

					if (checkin.isSuccess()) {
						checkin.setLatitude(bundle.getString("latitude"));
						checkin.setLongitude(bundle.getString("longitude"));
						if (Utils.isAutoCheckin(context))
							checkinDBHelper.insertCheckin(checkin);
						bundle.putString("checkinID", checkin.getCheckinID());
						bundle.putString("dateTime", checkin.getDateTime());
					}
				}

			} catch (Exception e) {

			} finally {
				checkinDBHelper.close();
			}
			return venue;
		}

		protected void onPostExecute(GenericResponseObject response) {
			/*
			 * if (results.get("success").equals("true")) {
			 * bundle.putString("venueName", venueNameText.getText()
			 * .toString()); bundle.putString("venueWebsite",
			 * venueWebsiteText.getText() .toString()); launchViewCheckin(); }
			 * else if (results.get("errors").equals(Constants.INVALID_SESSION))
			 * { Utils.makeToast(context, Constants.INVALID_SESSION,
			 * Toast.LENGTH_LONG); launchLogin(); } else {
			 * Utils.makeToast(context, results.get("errors"),
			 * Toast.LENGTH_LONG); } }
			 */
		}
	}
}
