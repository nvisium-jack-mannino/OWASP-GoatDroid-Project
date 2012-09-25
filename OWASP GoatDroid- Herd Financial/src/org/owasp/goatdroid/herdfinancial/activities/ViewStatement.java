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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.owasp.goatdroid.herdfinancial.R;
import org.owasp.goatdroid.herdfinancial.base.BaseActivity;
import org.owasp.goatdroid.herdfinancial.db.StatementDBHelper;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.misc.Constants;
import org.owasp.goatdroid.herdfinancial.misc.Utils;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewStatement extends BaseActivity {

	Context context;
	ListView listView;
	Bundle bundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_statement);
		context = this.getApplicationContext();
		listView = (ListView) findViewById(R.id.viewStatementListView);
		bundle = getIntent().getExtras();
		GetStatement getStatement = new GetStatement();
		getStatement.execute(null, null);
	}

	public void exportStatement(View v) {
		StatementDBHelper sdbh = new StatementDBHelper(context);

		try {
			FileWriter writer = new FileWriter(
					Environment.getExternalStorageDirectory()
							+ "/Herd_Financial_Statement.csv");
			PrintWriter printWriter = new PrintWriter(writer);
			printWriter.print(sdbh.getStatementCSV(bundle
					.getString("selection")));
			printWriter.close();
			Utils.makeToast(context, Constants.EXPORT_SUCCESS,
					Toast.LENGTH_LONG);
		} catch (IOException e) {
			Utils.makeToast(context, e.getMessage(), Toast.LENGTH_LONG);
		} finally {
			sdbh.close();
		}
	}

	public void launchLogin() {
		Intent intent = new Intent(context, Login.class);
		startActivity(intent);
	}

	private class GetStatement extends AsyncTask<Void, Void, String[]> {
		protected String[] doInBackground(Void... params) {

			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			StatementDBHelper sdbh = new StatementDBHelper(context);
			String[] statement;
			try {
				statement = sdbh.getStatementArray(bundle
						.getString("selection"));
			} finally {
				uidh.close();
				sdbh.close();
			}
			return statement;
		}

		public void onPostExecute(String[] statement) {

			if (statement.length == 0) {
				TextView emptyStatement = (TextView) findViewById(R.id.emptyStatementTextView);
				emptyStatement.setVisibility(1);
			} else {
				listView.setAdapter(new ArrayAdapter<String>(
						ViewStatement.this,
						android.R.layout.simple_list_item_1, statement));
			}
		}
	}
}
