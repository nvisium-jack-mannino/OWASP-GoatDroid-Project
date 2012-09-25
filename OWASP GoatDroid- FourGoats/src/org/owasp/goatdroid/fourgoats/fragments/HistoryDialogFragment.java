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
package org.owasp.goatdroid.fourgoats.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.activities.Login;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.javascriptinterfaces.ViewCheckinJSInterface;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.history.HistoryRequest;
import org.owasp.goatdroid.fourgoats.R;
import com.actionbarsherlock.app.SherlockDialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryDialogFragment extends SherlockDialogFragment {

	Context context;
	WebView webview;
	TextView noCheckinsTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.history, container, false);
		webview = (WebView) v.findViewById(R.id.historyWebView);
		WebSettings webSettings = webview.getSettings();
		webview.addJavascriptInterface(new ViewCheckinJSInterface(context),
				"jsInterface");
		webSettings.setJavaScriptEnabled(true);
		noCheckinsTextView = (TextView) v.findViewById(R.id.noCheckinsTextView);
		GetHistory getHistory = new GetHistory();
		getHistory.execute(null, null);
		return v;
	}

	public void launchLogin() {
		Intent intent = new Intent(context, Login.class);
		startActivity(intent);
	}

	public String generateHistoryHTML(
			ArrayList<HashMap<String, String>> historyData) {

		String historyTable = "";
		String venueName = "";
		String checkinID = "";
		String dateTime = "";
		String latitude = "";
		String longitude = "";
		String venueWebsite = "";

		if (historyData.size() > 1) {

			for (HashMap<String, String> checkin : historyData) {
				if (checkin.get("venueName") != null)
					venueName = checkin.get("venueName");
				if (checkin.get("checkinID") != null)
					checkinID = checkin.get("checkinID");
				if (checkin.get("dateTime") != null)
					dateTime = checkin.get("dateTime");
				if (checkin.get("latitude") != null)
					latitude = checkin.get("latitude");
				if (checkin.get("longitude") != null)
					longitude = checkin.get("longitude");
				if (checkin.get("venueWebsite") != null)
					venueWebsite = checkin.get("venueWebsite");

				String[] splitDateTime = dateTime.split(" ");
				historyTable += "<p><b>"
						+ venueName
						+ "</b><br><b>Date:</b> "
						+ splitDateTime[0]
						+ "<br><b>Time:</b> "
						+ splitDateTime[1]
						+ "<br><b>Latitude:</b> "
						+ latitude
						+ "<br><b>Longitude:</b> "
						+ longitude
						+ "<br>"
						+ "<button style=\"color: white; background-color:#2E9AFE\" "
						+ "type=\"button\" onclick=\"window.jsInterface.launchViewCheckinActivity('"
						+ venueName + "','" + venueWebsite + "','" + dateTime
						+ "','" + latitude + "','" + longitude + "','"
						+ checkinID + "')\">View Checkin</button><br>";
			}
		} else {
			historyTable += "<p><p>You have not checked in yet, grasshopper";
		}
		return historyTable;
	}

	private class GetHistory extends
			AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

		boolean success = false;
		String errors = "";
		String htmlResponse = "";

		protected ArrayList<HashMap<String, String>> doInBackground(
				Void... params) {

			ArrayList<HashMap<String, String>> historyData = new ArrayList<HashMap<String, String>>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			HistoryRequest rest = new HistoryRequest(context);
			try {
				if (sessionToken.equals("")) {
					errors = Constants.INVALID_SESSION;
				} else {
					historyData = rest.getHistory(sessionToken);
					if (historyData.get(0).get("success").equals("true")) {
						htmlResponse = generateHistoryHTML(historyData);
						success = true;
					} else
						errors = historyData.get(0).get("errors");
				}
			} catch (Exception e) {
				errors = Constants.COULD_NOT_CONNECT;
			} finally {
				uidh.close();
			}

			return historyData;
		}

		public void onPostExecute(ArrayList<HashMap<String, String>> results) {
			if (success) {
				webview.loadData(htmlResponse, "text/html", "UTF-8");
			} else if (errors.equals(Constants.INVALID_SESSION)) {
				launchLogin();
			} else if (errors.equals(Constants.NEVER_CHECKED_IN)) {
				noCheckinsTextView.setVisibility(1);
			} else {
				Utils.makeToast(context, errors, Toast.LENGTH_LONG);
			}
		}
	}
}