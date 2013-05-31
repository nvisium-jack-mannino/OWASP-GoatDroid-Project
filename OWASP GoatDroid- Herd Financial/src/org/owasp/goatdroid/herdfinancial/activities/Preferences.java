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

import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.base.BaseActivity;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Preferences extends BaseActivity {

	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		context = getApplicationContext();
	}

	public void launchSecretQuestionActivity(View v) {
		Intent intent = new Intent(Preferences.this, SetSecretQuestion.class);
		startActivity(intent);
	}

	public void launchAuthorizeDeviceActivity(View v) {
		Intent intent = new Intent(Preferences.this, Authorize.class);
		startActivity(intent);
	}

	public void launchAuthorizeFourGoats(View v) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("org.owasp.goatdroid.fourgoats",
				"org.owasp.goatdroid.fourgoats.activities.SocialAPIAuthentication"));
		startActivityForResult(intent, 1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		parseResult(this, requestCode, resultCode, data);
	}

	public void parseResult(Activity activity, int requestCode, int resultCode,
			Intent outputIntent) {

		Bundle bundle = outputIntent.getExtras();
		SharedPreferences fourGoatsInfo = context.getSharedPreferences(
				"fourgoats_api", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = fourGoatsInfo.edit();
		editor.putString("fourgoats_token", bundle.getString("sessionToken"));
		editor.commit();
	}
}
