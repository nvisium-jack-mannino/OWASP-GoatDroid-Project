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
import org.owasp.goatdroid.herdfinancial.base.BaseActivity;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.request.SecretQuestionsRequest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetSecretQuestionActivity extends BaseActivity {

	Context context;
	String answer1;
	String answer2;
	String answer3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_secret_question);
		context = getApplicationContext();
	}

	public void submitSecretQuestions(View v) {

		EditText answer1EditText = (EditText) findViewById(R.id.secretQuestion1EditText);
		EditText answer2EditText = (EditText) findViewById(R.id.secretQuestion2EditText);
		EditText answer3EditText = (EditText) findViewById(R.id.secretQuestion3EditText);
		answer1 = answer1EditText.getText().toString();
		answer2 = answer2EditText.getText().toString();
		answer3 = answer3EditText.getText().toString();

		if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty())
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
		else {
			SetQuestionAsyncTask task = new SetQuestionAsyncTask();
			task.execute(null, null);
		}
	}

	public void launchHome() {
		Intent intent = new Intent(context, HomeActivity.class);
		startActivity(intent);
	}

	public void launchLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	private class SetQuestionAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			SecretQuestionsRequest rest = new SecretQuestionsRequest(context);
			HashMap<String, String> secretQuestionData = new HashMap<String, String>();

			try {
				String sessionToken = uidh.getSessionToken();
				secretQuestionData = rest.setSecretQuestions(sessionToken,
						answer1, answer2, answer3);
				if (secretQuestionData.get("success").equals("true")) {
					uidh.updateAnswers(answer1, answer2, answer3);
				}
			} catch (Exception e) {
				secretQuestionData.put("errors", e.getMessage());
				secretQuestionData.put("success", "false");
			} finally {
				uidh.close();
			}
			return secretQuestionData;
		}

		protected void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Utils.makeToast(context,
						Constants.SET_SECRET_QUESTIONS_SUCCESS,
						Toast.LENGTH_LONG);
				launchHome();
			} else if (results.get("errors").equals(Constants.INVALID_SESSION))
				launchLogin();
			else
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
		}
	}
}
