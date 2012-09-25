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

import org.owasp.goatdroid.fourgoats.base.BaseActivity;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.R;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMS extends BaseActivity {

	Context context;
	Bundle bundle;
	EditText phoneNumberEditText;
	EditText smsMessageEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_sms);
		context = this.getApplicationContext();
		bundle = getIntent().getExtras();
		phoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);
		smsMessageEditText = (EditText) findViewById(R.id.smsMessageEditText);
		smsMessageEditText.setText("I checked in at "
				+ bundle.getString("venueName") + " on "
				+ bundle.getString("dateTime"));
	}

	public void sendSMS(View v) {

		SmsManager sms = SmsManager.getDefault();

		if (areFieldsCompleted()) {
			sms.sendTextMessage(phoneNumberEditText.getText().toString(), null,
					smsMessageEditText.getText().toString(), null, null);
			Utils.makeToast(context, Constants.TEXT_MESSAGE_SENT,
					Toast.LENGTH_LONG);
		} else
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
	}

	public boolean areFieldsCompleted() {

		if (!(phoneNumberEditText.getText().toString().equals("") || smsMessageEditText
				.getText().toString().equals("")))
			return true;
		else
			return false;
	}
}
