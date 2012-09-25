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
package org.owasp.goatdroid.fourgoats.base;

import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.activities.About;
import org.owasp.goatdroid.fourgoats.activities.AdminHome;
import org.owasp.goatdroid.fourgoats.activities.Home;
import org.owasp.goatdroid.fourgoats.activities.Login;
import org.owasp.goatdroid.fourgoats.activities.Preferences;
import org.owasp.goatdroid.fourgoats.activities.ViewProfile;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.login.LoginRequest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class BaseFragmentActivity extends SherlockFragmentActivity {

	protected Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getSupportActionBar().setIcon(R.drawable.ic_main);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			getSupportActionBar().setHomeButtonEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	// TODO check menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		context = this.getApplicationContext();
		return super.onCreateOptionsMenu(menu);
	}

	public void launchLogin() {
		Intent intent = new Intent(context, Login.class);
		startActivity(intent);
	}

	public void launchHome() {
		Intent intent = new Intent(context, Home.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			UserInfoDBHelper homeUIDH = new UserInfoDBHelper(context);
			Intent homeIntent;
			try {
				if (homeUIDH.getIsAdmin())
					homeIntent = new Intent(BaseFragmentActivity.this,
							AdminHome.class);
				else
					homeIntent = new Intent(BaseFragmentActivity.this,
							Home.class);
			} finally {
				homeUIDH.close();
			}
			startActivity(homeIntent);
			return true;
		} else if (itemId == R.id.preferences) {
			Intent intent = new Intent(BaseFragmentActivity.this,
					Preferences.class);
			startActivity(intent);
			return true;
		} else if (itemId == R.id.viewMyProfile) {
			Intent profileIntent = new Intent(BaseFragmentActivity.this,
					ViewProfile.class);
			Bundle bundle = new Bundle();
			UserInfoDBHelper profileUIDH = new UserInfoDBHelper(context);
			String userName = profileUIDH.getUserName();
			profileUIDH.close();
			bundle.putString("userName", userName);
			profileIntent.putExtras(bundle);
			startActivity(profileIntent);
			return true;
		} else if (itemId == R.id.logOut) {
			LogOutAsyncTask task = new LogOutAsyncTask();
			task.execute(null, null);
			return true;
		} else if (itemId == R.id.about) {
			Intent aboutIntent = new Intent(BaseFragmentActivity.this,
					About.class);
			startActivity(aboutIntent);
			return true;
		}
		return true;
	}

	public class LogOutAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			LoginRequest rest = new LoginRequest(context);
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			HashMap<String, String> logoutInfo = new HashMap<String, String>();

			try {
				logoutInfo = rest.logOut(uidh.getSessionToken());
				uidh.deleteInfo();
			} catch (Exception e) {
				logoutInfo.put("errors", e.getMessage());
				logoutInfo.put("success", "false");
			} finally {
				uidh.close();
			}
			return logoutInfo;
		}

		public void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Intent intent = new Intent(context, Login.class);
				startActivity(intent);
			} else if (results.get("errors").equals(Constants.INVALID_SESSION)) {
				Utils.makeToast(context, Constants.INVALID_SESSION,
						Toast.LENGTH_LONG);
				Intent intent = new Intent(context, Login.class);
				startActivity(intent);
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}
}