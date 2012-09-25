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
package org.owasp.goatdroid.fourgoats.services;

import org.owasp.goatdroid.fourgoats.db.CheckinDBHelper;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/*This allows users to view their path
 * through life
 */
public class LocationService extends Service {

	LocationManager locationManager;
	String latitude;
	String longitude;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		UserInfoDBHelper uidh = new UserInfoDBHelper(getApplicationContext());
		String autoCheckin = uidh.getPreferences().get("autoCheckin");

		if (autoCheckin.equals("true")) {
			getLocation();
			getLocationLoop();
		} else
			stopSelf();
	}

	public void getLocationLoop() {

		Thread t = new Thread() {
			public void run() {

				while (latitude == null && longitude == null) {
					/*
					 * We just loop until the service retrieves coords Does
					 * terrible things to batteries, too
					 */
				}

				while (true) {
					CheckinDBHelper cidh = new CheckinDBHelper(
							getApplicationContext());
					cidh.insertAutoCheckin(latitude, longitude,
							Utils.getCurrentDateTime());
					cidh.close();
					try {
						sleep(300000);
					} catch (InterruptedException e) {

					}
				}
			}
		};
		t.start();
	}

	public void getLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new MyLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, ll);
	}

	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {

			latitude = Double.toString(location.getLatitude());
			longitude = Double.toString(location.getLongitude());
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	}
}
