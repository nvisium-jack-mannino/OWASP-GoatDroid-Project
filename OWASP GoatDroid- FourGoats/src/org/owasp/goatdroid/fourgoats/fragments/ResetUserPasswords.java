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
package org.owasp.goatdroid.fourgoats.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.activities.DoAdminPasswordReset;
import org.owasp.goatdroid.fourgoats.activities.Login;
import org.owasp.goatdroid.fourgoats.adapter.SearchForFriendsAdapter;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.admin.AdminRequest;

import com.actionbarsherlock.app.SherlockFragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ResetUserPasswords extends SherlockFragment {

	Context context;
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = this.getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.reset_user_passwords, container,
				false);

		listView = (ListView) v.findViewById(R.id.usersListView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int myItemInt, long mylng) {
				String selectedFromList = (String) (listView
						.getItemAtPosition(myItemInt));
				String[] splitList = selectedFromList.split("\n");
				String userName = splitList[1];
				Intent intent = new Intent(getActivity(),
						DoAdminPasswordReset.class);
				Bundle userNameBundle = new Bundle();
				userNameBundle.putString("userName", userName);
				intent.putExtras(userNameBundle);
				startActivity(intent);
			}
		});

		SearchForUsers search = new SearchForUsers();
		search.execute(null, null);
		return v;
	}

	public String[] bindListView(ArrayList<HashMap<String, String>> userData) {

		// userName, firstName, lastName
		ArrayList<String> userArray = new ArrayList<String>();
		for (HashMap<String, String> user : userData) {
			if ((user.get("firstName") != null)
					&& (user.get("lastName") != null)
					&& (user.get("userName") != null))

				userArray.add(user.get("firstName") + " "
						+ user.get("lastName") + "\n" + user.get("userName"));
		}
		String[] users = new String[userArray.size()];
		users = userArray.toArray(users);
		return users;
	}

	private class SearchForUsers extends AsyncTask<Void, Void, String[]> {
		protected String[] doInBackground(Void... params) {

			ArrayList<HashMap<String, String>> userData = new ArrayList<HashMap<String, String>>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			uidh.close();
			AdminRequest rest = new AdminRequest(context);
			try {
				if (sessionToken.equals("")) {
					Intent intent = new Intent(getActivity(), Login.class);
					startActivity(intent);
				} else {
					userData = rest.getUsers(sessionToken);
					if (userData.size() > 0) {
						if (userData.get(0).get("success").equals("true")) {
							return bindListView(userData);
						} else
							Utils.makeToast(context,
									userData.get(1).get("errors"),
									Toast.LENGTH_LONG);
					} else {
						Utils.makeToast(context, Constants.WEIRD_ERROR,
								Toast.LENGTH_LONG);
					}
				}
			} catch (Exception e) {
				Intent intent = new Intent(getActivity(), Login.class);
				startActivity(intent);
			}
			return new String[0];
		}

		public void onPostExecute(String[] users) {
			if (getActivity() != null) {
				listView.setAdapter(new SearchForFriendsAdapter(getActivity(),
						users));
			}
		}
	}
}
