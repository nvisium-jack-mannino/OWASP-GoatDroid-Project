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

import java.util.HashMap;
import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.rest.login.LoginRequest;
import org.owasp.goatdroid.herdfinancial.services.StatementUpdateService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class Main extends Activity {

	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this.getApplicationContext();
		CheckSessionToken check = new CheckSessionToken();
		check.execute(null, null);
	}

	private class CheckSessionToken extends AsyncTask<Void, Void, Boolean> {
		protected Boolean doInBackground(Void... params) {

			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			LoginRequest rest = new LoginRequest(context);

			try {
				String sessionToken = uidh.getSessionToken();
				if (sessionToken.isEmpty())
					sessionToken = "0000000";
				String deviceID = Utils.getDeviceID(context);
				HashMap<String, String> result = rest
						.isDeviceAuthorizedOrSessionValid(sessionToken,
								deviceID);
				if (result.get("success").equals("true")) {
					/*
					 * If this is true but we get no token this implies that
					 * existing session was valid
					 */
					if (result.get("sessionToken").equals("")) {
						Intent serviceIntent = new Intent(context,
								StatementUpdateService.class);
						startService(serviceIntent);
						Intent intent = new Intent(Main.this, Home.class);
						startActivity(intent);
						return true;
						/*
						 * If we receive a session token back then the device
						 * was authorized and the session token was invalid
						 */
					} else {
						if (!result.get("sessionToken").equals("0")) {
							uidh.deleteInfo();
							uidh.insertSettings(result);
						}
						Intent serviceIntent = new Intent(context,
								StatementUpdateService.class);
						startService(serviceIntent);
						Intent intent = new Intent(Main.this, Home.class);
						startActivity(intent);
						return true;
					}
					/*
					 * False indicates that neither the session nor device ID
					 * were valid, and we need to enter our username/password to
					 * be granted authenticated status.
					 */
				} else {
					Intent intent = new Intent(Main.this, Login.class);
					startActivity(intent);
					return false;
				}
			} catch (Exception e) {
				Intent intent = new Intent(Main.this, Login.class);
				startActivity(intent);
				return false;
			} finally {
				uidh.close();
			}
		}
	}
}