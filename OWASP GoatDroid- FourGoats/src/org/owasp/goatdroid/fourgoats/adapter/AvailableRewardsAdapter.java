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
package org.owasp.goatdroid.fourgoats.adapter;

import org.owasp.goatdroid.fourgoats.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AvailableRewardsAdapter extends ArrayAdapter<String> {
	private final Activity activity;
	private final String[] values;

	static class ViewHolder {
		public TextView name;
		public TextView description;
		public TextView venue;
		public TextView latitude;
		public TextView longitude;
		public TextView checkins;
	}

	public AvailableRewardsAdapter(Activity activity, String[] values) {
		super(activity, R.layout.search_for_friends_item, values);
		this.activity = activity;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.available_rewards_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView.findViewById(R.id.name);
			viewHolder.description = (TextView) rowView
					.findViewById(R.id.description);
			viewHolder.venue = (TextView) rowView.findViewById(R.id.venue);
			viewHolder.latitude = (TextView) rowView
					.findViewById(R.id.latitude);
			viewHolder.longitude = (TextView) rowView
					.findViewById(R.id.longitude);
			viewHolder.checkins = (TextView) rowView
					.findViewById(R.id.checkins);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		String s = values[position];

		String[] tmp;
		String delimiter = "\n";
		tmp = s.split(delimiter);

		holder.name.setText(tmp[0]);
		holder.description.setText(tmp[1]);
		holder.venue.setText(tmp[2]);
		holder.latitude.setText(tmp[3]);
		holder.longitude.setText(tmp[4]);
		holder.checkins.setText(tmp[5]);

		return rowView;
	}

}