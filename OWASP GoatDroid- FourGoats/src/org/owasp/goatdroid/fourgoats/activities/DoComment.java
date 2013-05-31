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
import org.owasp.goatdroid.fourgoats.rest.comments.CommentsRequest;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DoComment extends BaseActivity {

	Context context;
	Bundle bundle;
	EditText commentEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_comment);
		context = getApplicationContext();
		commentEditText = (EditText) findViewById(R.id.commentEditText);
		bundle = getIntent().getExtras();
	}

	public void submitComment(View v) {

		if (!areFieldsCompleted()) {
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
		} else {
			DoCommentAsyncTask task = new DoCommentAsyncTask();
			task.execute(null, null);
		}
	}

	public boolean areFieldsCompleted() {

		if (!commentEditText.getText().toString().equals(""))
			return true;
		else
			return false;
	}

	private class DoCommentAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			HashMap<String, String> commentData = new HashMap<String, String>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			CommentsRequest rest = new CommentsRequest(context);
			try {
				commentData = rest.addComment(sessionToken, commentEditText
						.getText().toString(), bundle.getString("checkinID"));
			} catch (Exception e) {
				commentData.put("errors", e.getMessage());
			} finally {
				uidh.close();
			}
			return commentData;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Utils.makeToast(context, Constants.COMMENT_SUCCESS,
						Toast.LENGTH_LONG);
				launchViewCheckin(bundle);
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}

		public void launchViewCheckin(Bundle bundle) {
			Intent intent = new Intent(context, ViewCheckin.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
}
