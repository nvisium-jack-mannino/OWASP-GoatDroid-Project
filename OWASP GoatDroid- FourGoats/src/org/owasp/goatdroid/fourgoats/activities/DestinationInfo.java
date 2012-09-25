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
import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.base.BaseUnauthenticatedActivity;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class DestinationInfo extends BaseUnauthenticatedActivity {

	Context context;
	CheckBox isPublic;
	CheckBox autoCheckin;
	EditText hostEditText;
	EditText portEditText;
	EditText proxyHostEditText;
	EditText proxyPortEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.destination_info);
		context = this.getApplicationContext();
		HashMap<String, String> destinationMap = Utils
				.getDestinationInfoMap(context);
		HashMap<String, String> proxyMap = Utils.getProxyMap(context);
		hostEditText = (EditText) findViewById(R.id.hostEditText);
		portEditText = (EditText) findViewById(R.id.portEditText);
		proxyHostEditText = (EditText) findViewById(R.id.proxyHostEditText);
		proxyPortEditText = (EditText) findViewById(R.id.proxyPortEditText);
		hostEditText.setText(destinationMap.get("host"));
		portEditText.setText(destinationMap.get("port"));
		proxyHostEditText.setText(proxyMap.get("proxyHost"));
		proxyPortEditText.setText(proxyMap.get("proxyPort"));
	}

	public void saveDestinationInfo(View v) {

		if (hostEditText.getText().toString().equals("")
				|| portEditText.getText().toString().equals(""))
			Utils.makeToast(context, Constants.ALL_FIELDS_REQUIRED,
					Toast.LENGTH_LONG);
		else {
			Utils.writeDestinationInfo(context, hostEditText.getText()
					.toString(), portEditText.getText().toString());
			Utils.writeProxyInfo(context, proxyHostEditText.getText()
					.toString(), proxyPortEditText.getText().toString());
			Utils.makeToast(context, Constants.DESTINATION_INFORMATION_SUCCESS,
					Toast.LENGTH_LONG);
			Intent intent = new Intent(DestinationInfo.this, Login.class);
			startActivity(intent);
		}
	}
}
