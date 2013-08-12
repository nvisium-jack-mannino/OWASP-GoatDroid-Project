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
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.SSLCertificateValidation;
import org.owasp.goatdroid.fourgoats.rest.login.LoginRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

//Extends regular activity
//We don't want an options menu here
public class MainActivity extends Activity {

	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loader);
		context = this.getApplicationContext();
		/*
		 * This makes it easy to make SSL work because it makes the errors go
		 * away.
		 */
		SSLCertificateValidation.disable();
		/*
		 * Now, we make async calls
		 */
		CheckSessionToken check = new CheckSessionToken();
		check.execute(null, null);
	}

	private class CheckSessionToken extends AsyncTask<Void, Void, Boolean> {
		protected Boolean doInBackground(Void... params) {

			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			if (sessionToken.equals("")) {
				uidh.close();
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
				return false;

			} else {
				LoginRequest rest = new LoginRequest(context);
				try {
					if (rest.isSessionValid(sessionToken)) {
						boolean isAdmin = uidh.getIsAdmin();
						if (isAdmin) {
							Intent intent = new Intent(MainActivity.this,
									AdminHomeActivity.class);
							startActivity(intent);
						} else {
							Intent intent = new Intent(MainActivity.this, HomeActivity.class);
							startActivity(intent);
						}
						return true;
					} else {
						uidh.deleteInfo();
						Intent intent = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(intent);
						return false;
					}
				} catch (Exception e) {
					uidh.close();
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					startActivity(intent);
					return false;
				} finally {
					uidh.close();
				}
			}
		}
	}
}