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
import android.widget.DatePicker;

public class SelectStatementActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_statement);
	}

	public void getStatement(View v) {
		DatePicker startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
		DatePicker endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);
		/*
		 * These convert the DatePicker into proper YYYY-MM-DD format
		 */
		String startMonth = Integer.toString(startDatePicker.getMonth() + 1);
		if (startMonth.length() < 2)
			startMonth = "0" + startMonth;
		String startDay = Integer.toString(startDatePicker.getDayOfMonth());
		if (startDay.length() < 2)
			startDay = "0" + startDay;
		String endMonth = Integer.toString(endDatePicker.getMonth() + 1);
		if (endMonth.length() < 2)
			endMonth = "0" + endMonth;
		String endDay = Integer.toString(endDatePicker.getDayOfMonth());
		if (endDay.length() < 2)
			endDay = "0" + endDay;
		/*
		 * Now, we build the entire date string
		 */
		String startDate = Integer.toString(startDatePicker.getYear()) + "-"
				+ startMonth + "-" + startDay;
		String endDate = Integer.toString(endDatePicker.getYear()) + "-"
				+ endMonth + "-" + endDay;
		Bundle bundle = new Bundle();
		bundle.putString("selection", "date between date('" + startDate
				+ "') and date('" + endDate + "')");
		Intent intent = new Intent(this, ViewStatementActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
