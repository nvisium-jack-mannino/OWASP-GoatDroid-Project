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
package org.owasp.goatdroid.herdfinancial.base;

import java.util.HashMap;

import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.activities.AboutActivity;
import org.owasp.goatdroid.herdfinancial.activities.LoginActivity;
import org.owasp.goatdroid.herdfinancial.activities.PreferencesActivity;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.request.LoginRequest;
import org.owasp.goatdroid.herdfinancial.services.StatementUpdateService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends Activity {

	Context context;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		context = this.getApplicationContext();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.preferences:
			Intent intent = new Intent(BaseActivity.this,
					PreferencesActivity.class);
			startActivity(intent);
			return true;
		case R.id.about:
			Intent intent2 = new Intent(BaseActivity.this, AboutActivity.class);
			startActivity(intent2);
			return true;
		case R.id.logOut:
			LogoutAsyncTask task = new LogoutAsyncTask();
			task.execute(null, null);
			return true;
		}
		return true;
	}

	public void launchLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	private class LogoutAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> logoutInfo = new HashMap<String, String>();
			LoginRequest rest = new LoginRequest(context);
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			try {
				logoutInfo = rest.logOut();
			} catch (Exception e) {
				logoutInfo.put("errors", e.getMessage());
				logoutInfo.put("success", "false");
			} finally {
				Intent intent = new Intent(context,
						StatementUpdateService.class);
				stopService(intent);
				uidh.close();
			}
			return logoutInfo;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				launchLogin();
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
				launchLogin();
			}
		}
	}
}
