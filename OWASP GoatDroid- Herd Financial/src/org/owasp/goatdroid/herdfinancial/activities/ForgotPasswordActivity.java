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
import org.owasp.goatdroid.herdfinancial.base.BaseUnauthenticatedActivity;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import org.owasp.goatdroid.herdfinancial.request.ForgotPasswordRequest;
import org.owasp.goatdroid.herdfinancial.responseobjects.GenericResponseObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ForgotPasswordActivity extends BaseUnauthenticatedActivity {

	int selectedPosition = 0;
	String dbAnswer;
	String userName;
	String answer;
	EditText userNameEditText;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);

		Spinner spinner = (Spinner) findViewById(R.id.secretQuestionSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.secret_questions,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		context = getApplicationContext();
	}

	public void submitAnswer(View v) {

		EditText answerEditText = (EditText) findViewById(R.id.secretQuestionAnswerEditText);
		userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		answer = answerEditText.getText().toString();
		SubmitAsyncTask task = new SubmitAsyncTask();
		task.execute(null, null);
	}

	public void verifyPasswordResetCode(View v) {

		Intent intent = new Intent(ForgotPasswordActivity.this,
				VerifyPasswordResetCodeActivity.class);
		startActivity(intent);
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			/*
			 * We add 1 to the position because we start counting at 1 to
			 * reference the actual answer
			 */
			selectedPosition = parent.getSelectedItemPosition() + 1;
		}

		public void onNothingSelected(AdapterView parent) {
		}
	}

	private class SubmitAsyncTask extends
			AsyncTask<Void, Void, GenericResponseObject> {

		@Override
		protected GenericResponseObject doInBackground(Void... params) {

			GenericResponseObject response = new GenericResponseObject();
			ForgotPasswordRequest rest = new ForgotPasswordRequest(context);
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);

			try {
				dbAnswer = uidh.getSecretQuestionAnswer(selectedPosition);
				userName = uidh.getUserName();
				if (!dbAnswer.equals(answer)) {
					forgotPasswordData.put("errors",
							Constants.SECRET_QUESTION_NOT_MATCH);
					forgotPasswordData.put("success", "false");
				} else if (!userName.equals(userNameEditText.getText()
						.toString())) {
					forgotPasswordData
							.put("errors", Constants.INVALID_USERNAME);
					forgotPasswordData.put("success", "false");
				} else
					forgotPasswordData = rest.requestCode(userName,
							Integer.toString(selectedPosition), answer);
			} catch (NullPointerException e) {
				forgotPasswordData.put("errors", Constants.NO_SECRET_QUESTIONS);
				forgotPasswordData.put("success", "false");
			} catch (Exception e) {
				forgotPasswordData.put("errors", e.getMessage());
				forgotPasswordData.put("success", "false");
			} finally {
				uidh.close();
			}
			return forgotPasswordData;
		}

		protected void onPostExecute(GenericResponseObject response) {
			if (response.isSuccess()) {
				Utils.makeToast(context, Constants.SECRET_QUESTION_SUCCESS,
						Toast.LENGTH_LONG);
				Intent intent = new Intent(ForgotPasswordActivity.this,
						LoginActivity.class);
				startActivity(intent);
			} else
				Utils.makeToast(context,
						Utils.mergeArrayList(response.getErrors()),
						Toast.LENGTH_LONG);
		}
	}
}
