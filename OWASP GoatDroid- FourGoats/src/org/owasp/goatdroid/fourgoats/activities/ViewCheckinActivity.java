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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.base.BaseActivity;
import org.owasp.goatdroid.fourgoats.javascriptinterfaces.SmsJSInterface;
import org.owasp.goatdroid.fourgoats.javascriptinterfaces.ViewCheckinJSInterface;
import org.owasp.goatdroid.fourgoats.javascriptinterfaces.WebViewJSInterface;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.ViewCheckinRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.CheckinComments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class ViewCheckinActivity extends BaseActivity {

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

	public String generateViewCheckinHTML(
			ArrayList<HashMap<String, String>> commentData) {

		String html = "";

		html += "<p><b>" + bundle.getString("venueName") + "</b></p>";
		String[] dateTime = bundle.getString("dateTime").split(" ");
		html += "<p><b>Date:</b> " + dateTime[0] + " <b>Time:</b> "
				+ dateTime[1] + "</p>";
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
		if (commentData != null)
			html += generateComments(commentData);
		return html;
	}

	public String generateComments(
			ArrayList<HashMap<String, String>> commentList) {

		String commentsTable = "<b><big>Comments:</big></b><p>";

		for (HashMap<String, String> comment : commentList) {
			String[] dateTime = comment.get("dateTime").split(" ");
			commentsTable += "<p><b>" + comment.get("firstName") + " "
					+ comment.get("lastName") + "</b><br>" + dateTime[0]
					+ "<br>" + dateTime[1] + "<br>";
			commentsTable += "<b>\"" + comment.get("comment") + "\"</b><br>";
			commentsTable += "<button style=\"color: white; background-color:#2E9AFE\" type=\"button\" onclick=\"window.viewCheckinJSInterface.deleteComment('"
					+ comment.get("commentID")
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

		return commentsTable;
	}

	public void launchLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	private class GetCommentData extends AsyncTask<Void, Void, CheckinComments> {
		protected CheckinComments doInBackground(Void... params) {

			ViewCheckinRequest rest = new ViewCheckinRequest(context);
			CheckinComments comments = new CheckinComments();
			try {
				comments = rest.getCheckin(bundle.getString("checkinID"));
			} catch (Exception e) {
				comments.setErrors(new ArrayList<String>(Arrays.asList(e
						.getMessage())));
			}
			return comments;
		}

		public void onPostExecute(CheckinComments comments) {
			if (comments.isSuccess()) {
				webview.loadData(
						generateViewCheckinHTML(comments.getComments()),
						"text/html", "UTF-8");
			} else {
				Utils.makeToast(context,
						Utils.mergeArrayList(comments.getErrors()),
						Toast.LENGTH_LONG);
			}
		}
	}
}
