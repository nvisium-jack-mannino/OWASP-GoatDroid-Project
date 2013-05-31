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
import org.owasp.goatdroid.fourgoats.javascriptinterfaces.SmsJSInterface;
import org.owasp.goatdroid.fourgoats.javascriptinterfaces.ViewCheckinJSInterface;
import org.owasp.goatdroid.fourgoats.javascriptinterfaces.WebViewJSInterface;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.viewcheckin.ViewCheckinRequest;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class ViewCheckin extends BaseActivity {

	Context context;
	Bundle bundle;
	String sessionToken;
	WebView webview;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_checkin);
		context = getApplicationContext();
		bundle = getIntent().getExtras();
		webview = (WebView) findViewById(R.id.viewCheckinWebView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new SmsJSInterface(this),
				"smsJSInterface");
		webview.addJavascriptInterface(new ViewCheckinJSInterface(this),
				"viewCheckinJSInterface");
		webview.addJavascriptInterface(new WebViewJSInterface(this),
				"webViewJSInterface");
		GetCommentData getComments = new GetCommentData();
		getComments.execute(null, null);
	}

	public String generateViewCheckinHTML(HashMap<String, String> commentData) {

		String html = "";

		html += "<p><b>" + bundle.getString("venueName") + "</b></p>";
		String[] dateTimeArray = bundle.getString("dateTime").split(" ");
		html += "<p><b>Date:</b> " + dateTimeArray[0] + " <b>Time:</b> "
				+ dateTimeArray[1] + "</p>";
		html += "<button style=\"color: white; background-color:#2E9AFE\" type=\"button\" "
				+ "onclick=\"window.webViewJSInterface.launchWebView('"
				+ bundle.getString("venueWebsite")
				+ "')\">"
				+ "Visit Website</button><br><br>";
		html += "<button style=\"color: white; background-color:#2E9AFE\" type=\"button\" "
				+ "onclick=\"window.smsJSInterface.launchSendSMSActivity('"
				+ bundle.getString("venueName")
				+ "','"
				+ bundle.getString("dateTime")
				+ "')\">"
				+ "Text This To A Friend</button><p>";
		html += "<button style=\"color: white; background-color:#2E9AFE\" type=\"button\" onclick=\"window.viewCheckinJSInterface.launchDoCommentActivity('"
				+ bundle.getString("venueName")
				+ "','"
				+ bundle.getString("venueWebsite")
				+ "','"
				+ bundle.getString("dateTime")
				+ "','"
				+ bundle.getString("latitude")
				+ "','"
				+ bundle.getString("longitude")
				+ "','"
				+ bundle.getString("checkinID")
				+ "')\">"
				+ "Leave a Comment</button><br><br>";
		html += generateComments(commentData);
		return html;
	}

	public String generateComments(HashMap<String, String> commentData) {

		String commentsTable = "";
		String firstName;
		String lastName;
		String[] dateTime;
		String date;
		String time;
		String comment;
		if (commentData.size() > 3) {

			// really ugly way to dodge the = 0 scenario
			int totalSize = 0;
			if (commentData.size() / 6 - 3 == -2)
				totalSize = 1;
			else
				totalSize = commentData.size() / 6;

			commentsTable += "<b><big>Comments:</big></b><p>";

			// 6 fields to parse - 3 (venueName, venueWebsite, success
			for (int count = 0; count < totalSize; count++) {
				firstName = commentData.get("firstName" + count);
				lastName = commentData.get("lastName" + count);
				dateTime = commentData.get("dateTime" + count).split(" ");
				date = dateTime[0];
				time = dateTime[1];
				comment = commentData.get("comment" + count);
				commentsTable += "<p><b>" + firstName + " " + lastName
						+ "</b><br>" + date + "<br>" + time + "<br>";
				commentsTable += "<b>\"" + comment + "\"</b><br>";
				commentsTable += "<button style=\"color: white; background-color:#2E9AFE\" type=\"button\" onclick=\"window.viewCheckinJSInterface.deleteComment('"
						+ commentData.get("commentID" + count)
						+ "','"
						+ bundle.getString("venueName")
						+ "','"
						+ bundle.getString("venueWebsite")
						+ "','"
						+ bundle.getString("dateTime")
						+ "','"
						+ bundle.getString("latitude")
						+ "','"
						+ bundle.getString("longitude")
						+ "','"
						+ bundle.getString("checkinID")
						+ "')\">"
						+ "Delete Comment</button><br>";
			}
		}

		return commentsTable;
	}

	public void launchLogin() {
		Intent intent = new Intent(context, Login.class);
		startActivity(intent);
	}

	private class GetCommentData extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> commentData = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			ViewCheckinRequest rest = new ViewCheckinRequest(context);

			try {
				commentData = rest.getCheckin(sessionToken,
						bundle.getString("checkinID"));
				if (commentData.get("success").equals("true")) {
					commentData.put("htmlResponse",
							generateViewCheckinHTML(commentData));
				}
			} catch (Exception e) {
				commentData.put("errors", e.getMessage());
				commentData.put("success", "false");
			} finally {
				uidh.close();
			}
			return commentData;
		}

		public void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals(("true"))) {
				webview.loadData(results.get("htmlResponse"), "text/html",
						"UTF-8");
			} else if (results.get("errors").equals(Constants.INVALID_SESSION)) {
				launchLogin();
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}
}
