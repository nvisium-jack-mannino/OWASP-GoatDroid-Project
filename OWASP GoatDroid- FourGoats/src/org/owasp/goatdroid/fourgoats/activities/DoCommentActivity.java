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
import org.owasp.goatdroid.fourgoats.base.BaseActivity;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.CommentsRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DoCommentActivity extends BaseActivity {

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
			AsyncTask<Void, Void, GenericResponseObject> {

		@Override
		protected GenericResponseObject doInBackground(Void... params) {

			GenericResponseObject response = new GenericResponseObject();
			CommentsRequest rest = new CommentsRequest(context);
			try {
				response = rest.addComment(
						commentEditText.getText().toString(),
						bundle.getString("checkinID"));
			} catch (Exception e) {
				e.getMessage();
			}
			return response;
		}

		protected void onPostExecute(GenericResponseObject response) {
			if (response.isSuccess()) {
				Utils.makeToast(context, Constants.COMMENT_SUCCESS,
						Toast.LENGTH_LONG);
				launchViewCheckin(bundle);
			} else {
				Utils.makeToast(context,
						Utils.mergeArrayList(response.getErrors()),
						Toast.LENGTH_LONG);
			}
		}

		public void launchViewCheckin(Bundle bundle) {
			Intent intent = new Intent(context, ViewCheckinActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
}
