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
import org.owasp.goatdroid.fourgoats.misc.SSLCertificateValidation;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.LoginRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.Login;

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
		CheckAuthToken check = new CheckAuthToken();
		check.execute(null, null);
	}

	private class CheckAuthToken extends AsyncTask<Void, Void, Boolean> {
		protected Boolean doInBackground(Void... params) {

			String authToken = Utils.getAuthToken(context);
			if (authToken.equals("")) {
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent);
				return false;
			} else {
				LoginRequest rest = new LoginRequest(context);
				try {
					Login login = rest.isAuthTokenValid();
					if (login.isSuccess()) {
						if (Utils.isAdmin(context)) {
							Intent intent = new Intent(MainActivity.this,
									AdminHomeActivity.class);
							startActivity(intent);
						} else {
							Intent intent = new Intent(MainActivity.this,
									HomeActivity.class);
							startActivity(intent);
						}
						return true;
					} else {
						Intent intent = new Intent(MainActivity.this,
								LoginActivity.class);
						startActivity(intent);
						return false;
					}
				} catch (Exception e) {
					Intent intent = new Intent(MainActivity.this,
							LoginActivity.class);
					startActivity(intent);
					return false;
				}
			}
		}
	}
}