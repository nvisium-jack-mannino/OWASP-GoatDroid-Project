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
package org.owasp.goatdroid.fourgoats.fragments;

import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.activities.AddVenueActivity;
import org.owasp.goatdroid.fourgoats.activities.ViewCheckinActivity;
import org.owasp.goatdroid.fourgoats.db.CheckinDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.CheckinRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.Checkin;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class DoCheckin extends SherlockFragment {

	Context context;
	TextView gpsCoordsText;
	String latitude;
	String longitude;
	Button sendCheckin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		getLocation();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.do_checkin, container, false);
		gpsCoordsText = (TextView) v.findViewById(R.id.gpsCoords);
		sendCheckin = (Button) v.findViewById(R.id.button1);
		sendCheckin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendCheckin(v);
			}
		});

		return v;
	}

	public void getLocation() {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}

	public void sendCheckin(View v) {
		if (gpsCoordsText.getText().toString()
				.startsWith("Getting your location")) {
			Utils.makeToast(context, Constants.NO_LOCATION, Toast.LENGTH_LONG);
		} else {
			DoCheckinAsyncTask checkin = new DoCheckinAsyncTask();
			checkin.execute(null, null);
		}

	}

	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {

			latitude = Double.toString(location.getLatitude());
			longitude = Double.toString(location.getLongitude());
			gpsCoordsText.setText("Latitude: " + latitude + "\n\nLongitude: "
					+ longitude);
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	}

	private class DoCheckinAsyncTask extends AsyncTask<Void, Void, Checkin> {

		@Override
		protected Checkin doInBackground(Void... params) {

			Checkin checkin = new Checkin();
			CheckinRequest rest = new CheckinRequest(context);

			try {
				checkin = rest.doCheckin(latitude, longitude);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return checkin;
		}

		protected void onPostExecute(Checkin checkin) {

			if (checkin.isSuccess()) {
				CheckinDBHelper db = new CheckinDBHelper(context);
				checkin.setLatitude(latitude);
				checkin.setLongitude(longitude);
				db.insertCheckin(checkin);
				Bundle bundle = new Bundle();
				bundle.putString("checkinID", checkin.getCheckinID());
				bundle.putString("venueName", checkin.getVenueName());
				bundle.putString("venueWebsite", checkin.getVenueWebsite());
				bundle.putString("dateTime", checkin.getDateTime());
				bundle.putString("latitude", checkin.getLatitude());
				bundle.putString("longitude", checkin.getLongitude());
				launchViewCheckin(bundle);
				Utils.makeToast(context, Constants.CHECKIN_GREAT_SUCCESS,
						Toast.LENGTH_LONG);
				/*
				 * Check for if a reward was earned by this checkin
				 */
			} else {
				for (String error : checkin.getErrors()) {
					if (error.equals(Constants.VENUE_DOESNT_EXIST)) {
						Utils.makeToast(context, Constants.VENUE_DOESNT_EXIST,
								Toast.LENGTH_LONG);
						Bundle bundle = new Bundle();
						bundle.putString("latitude", latitude);
						bundle.putString("longitude", longitude);
						launchAddVenue(bundle);
						break;
					}
				}

			}
		}

		public void launchViewCheckin(Bundle bundle) {
			Intent intent = new Intent(context, ViewCheckinActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}

		public void launchAddVenue(Bundle bundle) {
			Intent intent = new Intent(context, AddVenueActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
}