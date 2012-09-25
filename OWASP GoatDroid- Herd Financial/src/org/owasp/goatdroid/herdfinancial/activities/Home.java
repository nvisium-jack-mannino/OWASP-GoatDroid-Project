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

import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.base.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends BaseActivity {
	ListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		listview = (ListView) findViewById(R.id.listView1);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int myItemInt, long mylng) {
				String selectedFromList = (String) (listview
						.getItemAtPosition(myItemInt));

				if (selectedFromList.equals("Check Balance")) {
					Intent intent = new Intent(Home.this, GetBalance.class);
					startActivity(intent);
				} else if (selectedFromList.equals("Transfer Funds")) {
					Intent intent = new Intent(Home.this, Transfer.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(Home.this, SelectStatement.class);
					startActivity(intent);
				}
			}
		});
	}
}
