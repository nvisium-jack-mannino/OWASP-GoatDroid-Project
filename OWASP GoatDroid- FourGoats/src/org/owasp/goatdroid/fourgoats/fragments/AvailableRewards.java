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
import org.owasp.goatdroid.fourgoats.activities.Login;
import org.owasp.goatdroid.fourgoats.adapter.AvailableRewardsAdapter;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.rewards.RewardsRequest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;

public class AvailableRewards extends SherlockFragment {

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

		View v = inflater.inflate(R.layout.available_rewards, container, false);

		listView = (ListView) v.findViewById(R.id.availableRewardsListView);

		GetAvailableRewards availableRewards = new GetAvailableRewards();
		availableRewards.execute(null, null);
		return v;
	}

	public String[] bindListView(ArrayList<HashMap<String, String>> rewardData) {

		// userID, firstName, lastName
		ArrayList<String> rewardArray = new ArrayList<String>();
		for (HashMap<String, String> reward : rewardData) {
			if ((reward.get("rewardName") != null)
					&& (reward.get("rewardDescription") != null)
					&& (reward.get("venueName") != null)
					&& (reward.get("latitude") != null)
					&& (reward.get("longitude") != null)
					&& (reward.get("checkinsRequired") != null))
				rewardArray.add(reward.get("rewardName") + "\n"
						+ reward.get("rewardDescription") + "\nVenue: "
						+ reward.get("venueName") + "\nLatitude: "
						+ reward.get("latitude") + "\nLongitude: "
						+ reward.get("longitude") + "\nCheckins Required: "
						+ reward.get("checkinsRequired"));
		}
		String[] rewards = new String[rewardArray.size()];
		rewards = rewardArray.toArray(rewards);
		return rewards;
	}

	private class GetAvailableRewards extends AsyncTask<Void, Void, String[]> {
		protected String[] doInBackground(Void... params) {

			ArrayList<HashMap<String, String>> rewardData = new ArrayList<HashMap<String, String>>();
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			String sessionToken = uidh.getSessionToken();
			uidh.close();
			RewardsRequest rest = new RewardsRequest(context);
			try {
				if (sessionToken.equals("")) {
					Intent intent = new Intent(getActivity(), Login.class);
					startActivity(intent);
					return new String[0];

				} else {
					rewardData = rest.getAllRewards(sessionToken);
					if (rewardData.size() > 1)
						return bindListView(rewardData);

					else {
						Utils.makeToast(context, Constants.WEIRD_ERROR,
								Toast.LENGTH_LONG);
						return new String[0];
					}
				}
			} catch (Exception e) {
				Intent intent = new Intent(getActivity(), Login.class);
				startActivity(intent);
				return new String[0];
			}
		}

		public void onPostExecute(String[] rewards) {

			if (getActivity() != null) {
				listView.setAdapter(new AvailableRewardsAdapter(getActivity(),
						rewards));
			}
		}
	}

}