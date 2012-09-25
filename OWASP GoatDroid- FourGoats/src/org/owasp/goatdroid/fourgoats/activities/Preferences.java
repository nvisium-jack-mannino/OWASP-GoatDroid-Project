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
import org.owasp.goatdroid.fourgoats.rest.preferences.PreferencesRequest;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Preferences extends BaseActivity {

	Context context;
	CheckBox isPublic;
	CheckBox autoCheckin;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		context = this.getApplicationContext();
		isPublic = (CheckBox) findViewById(R.id.publicProfileCheckBox);
		autoCheckin = (CheckBox) findViewById(R.id.autoCheckinCheckBox);
		GetExistingPreferences existingPreferences = new GetExistingPreferences();
		existingPreferences.execute(null, null);
	}

	public void submitChanges(View v) {

		UpdatePreferencesAsyncTask task = new UpdatePreferencesAsyncTask();
		task.execute(null, null);
	}

	public void launchHome(String isAdmin) {
		Intent intent;
		if (isAdmin.equals("true"))
			intent = new Intent(Preferences.this, AdminHome.class);
		else
			intent = new Intent(Preferences.this, Home.class);
		startActivity(intent);
	}

	private class GetExistingPreferences extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> preferencesData = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			preferencesData = uidh.getPreferences();
			uidh.close();

			if (sessionToken.equals("")) {
				Intent intent = new Intent(Preferences.this, Login.class);
				startActivity(intent);
				return preferencesData;

			} else if (preferencesData.size() > 0) {
				return preferencesData;

			} else {
				Utils.makeToast(context, Constants.WEIRD_ERROR,
						Toast.LENGTH_LONG);
				return preferencesData;
			}
		}

		public void onPostExecute(HashMap<String, String> preferences) {

			if (preferences.get("isPublic").equals("true"))
				isPublic.setChecked(true);
			else
				isPublic.setChecked(false);
			if (preferences.get("autoCheckin").equals("true"))
				autoCheckin.setChecked(true);
			else
				autoCheckin.setChecked(false);
		}
	}

	private class UpdatePreferencesAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			uidh.updatePreferences(Boolean.toString(isPublic.isChecked()),
					Boolean.toString(autoCheckin.isChecked()));
			PreferencesRequest rest = new PreferencesRequest(context);
			HashMap<String, String> preferenceInfo = new HashMap<String, String>();

			try {
				String sessionToken = uidh.getSessionToken();
				preferenceInfo = rest.updatePreferences(sessionToken,
						Boolean.toString(isPublic.isChecked()),
						Boolean.toString(autoCheckin.isChecked()));
				preferenceInfo.put("isAdmin",
						Boolean.toString(uidh.getIsAdmin()));
			} catch (Exception e) {
				preferenceInfo.put("errors", e.getMessage());
				preferenceInfo.put("success", "false");
			} finally {
				uidh.close();
			}
			return preferenceInfo;
		}

		public void onPostExecute(HashMap<String, String> results) {

			if (results.get("success").equals("true")) {
				Utils.makeToast(context, Constants.PREFERENCES_UPDATE_SUCCESS,
						Toast.LENGTH_LONG);
				launchHome(results.get("isAdmin"));
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}
}
