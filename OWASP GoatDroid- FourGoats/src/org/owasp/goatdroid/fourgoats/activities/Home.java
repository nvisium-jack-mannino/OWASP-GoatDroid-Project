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

import org.owasp.goatdroid.fourgoats.base.BaseActivity;
import org.owasp.goatdroid.fourgoats.services.LocationService;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Home extends BaseActivity {

	ListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle("Home");
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		setContentView(R.layout.home);
		Intent locationServiceIntent = new Intent(Home.this,
				LocationService.class);
		startService(locationServiceIntent);
	}

	public void launchCheckins(View v) {
		Intent intent = new Intent(Home.this, Checkins.class);
		startActivity(intent);
	}

	public void launchFriends(View v) {
		Intent intent = new Intent(Home.this, Friends.class);
		startActivity(intent);
	}

	public void launchRewards(View v) {
		Intent intent = new Intent(Home.this, Rewards.class);
		startActivity(intent);
	}
}