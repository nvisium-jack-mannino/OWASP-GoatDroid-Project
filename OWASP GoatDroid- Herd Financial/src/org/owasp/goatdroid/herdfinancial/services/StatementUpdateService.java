package org.owasp.goatdroid.herdfinancial.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.owasp.goatdroid.herdfinancial.db.StatementDBHelper;
import org.owasp.goatdroid.herdfinancial.db.UserInfoDBHelper;
import org.owasp.goatdroid.herdfinancial.request.StatementsRequest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StatementUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		getStatementUpdates();
	}

	public void getStatementUpdates() {

		Thread t = new Thread() {
			public void run() {

				while (true) {
					doStatementRequest();
					try {
						sleep(20000);
					} catch (InterruptedException e) {

					}
				}
			}
		};
		t.start();
	}

	public void doStatementRequest() {
		StatementsRequest request = new StatementsRequest(
				getApplicationContext());
		UserInfoDBHelper uidh = new UserInfoDBHelper(getApplicationContext());
		StatementDBHelper sdbh = new StatementDBHelper(getApplicationContext());
		try {
			ArrayList<HashMap<String, String>> transactions = request
					.getStatementUpdate(uidh.getSessionToken(),
							uidh.getAccountNumber());
			String userName = uidh.getUserName();
			for (HashMap<String, String> transaction : transactions) {
				transaction.put("userName", userName);
				sdbh.insert(transaction);
			}
		} catch (Exception e) {
			e.getMessage();

		} finally {
			uidh.close();
			sdbh.close();
		}
	}
}
