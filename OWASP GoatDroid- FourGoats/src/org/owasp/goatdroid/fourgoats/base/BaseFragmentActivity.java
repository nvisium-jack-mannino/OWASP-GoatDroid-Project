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

import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.activities.AboutActivity;
import org.owasp.goatdroid.fourgoats.activities.AdminHomeActivity;
import org.owasp.goatdroid.fourgoats.activities.HomeActivity;
import org.owasp.goatdroid.fourgoats.activities.LoginActivity;
import org.owasp.goatdroid.fourgoats.activities.PreferencesActivity;
import org.owasp.goatdroid.fourgoats.activities.ViewProfileActivity;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.LoginRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;
import org.owasp.goatdroid.fourgoats.responseobjects.ResponseObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

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
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	public void launchHome() {
		Intent intent = new Intent(context, HomeActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			Intent homeIntent;
			if (Utils.isAdmin(context))
				homeIntent = new Intent(BaseFragmentActivity.this,
						AdminHomeActivity.class);
			else
				homeIntent = new Intent(BaseFragmentActivity.this,
						HomeActivity.class);
			startActivity(homeIntent);
			return true;
		} else if (itemId == R.id.preferences) {
			Intent intent = new Intent(BaseFragmentActivity.this,
					PreferencesActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == R.id.viewMyProfile) {
			Intent profileIntent = new Intent(BaseFragmentActivity.this,
					ViewProfileActivity.class);
			Bundle bundle = new Bundle();

			bundle.putString("userName", Utils.getUsername(context));
			profileIntent.putExtras(bundle);
			startActivity(profileIntent);
			return true;
		} else if (itemId == R.id.logOut) {
			LogOutAsyncTask task = new LogOutAsyncTask();
			task.execute(null, null);
			return true;
		} else if (itemId == R.id.about) {
			Intent aboutIntent = new Intent(BaseFragmentActivity.this,
					AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		}
		return true;
	}

	public class LogOutAsyncTask extends AsyncTask<Void, Void, ResponseObject> {
		protected ResponseObject doInBackground(Void... params) {

			LoginRequest rest = new LoginRequest(context);

			try {
				return rest.logOut();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new GenericResponseObject();
		}

		public void onPostExecute(ResponseObject response) {
			/*
			 * if (results.get("success").equals("true")) { Intent intent = new
			 * Intent(context, LoginActivity.class); startActivity(intent); }
			 * else if (results.get("errors").equals(Constants.INVALID_SESSION))
			 * { Utils.makeToast(context, Constants.INVALID_SESSION,
			 * Toast.LENGTH_LONG); Intent intent = new Intent(context,
			 * LoginActivity.class); startActivity(intent); } else {
			 * Utils.makeToast(context, results.get("errors"),
			 * Toast.LENGTH_LONG); }
			 */
		}
	}
}