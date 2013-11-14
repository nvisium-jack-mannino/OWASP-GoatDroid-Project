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
package org.owasp.goatdroid.herdfinancial.misc;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.owasp.goatdroid.herdfinancial.misc.Constants;

public class Utils {

	static public void makeToast(Context context, String message, int duration) {

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	static public String getCurrentDateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new java.text.SimpleDateFormat(
				Constants.DATE_FORMAT);
		return df.format(cal.getTime());
	}

	static public String getDestinationInfo(Context context) {

		String destinationInfo = "";
		SharedPreferences prefs = context.getSharedPreferences(
				"destination_info", Context.MODE_WORLD_READABLE);
		destinationInfo += prefs.getString("host", "");
		destinationInfo += ":";
		destinationInfo += prefs.getString("port", "");
		return destinationInfo;
	}

	static public HashMap<String, String> getDestinationInfoMap(Context context) {

		HashMap<String, String> map = new HashMap<String, String>();
		SharedPreferences prefs = context.getSharedPreferences(
				"destination_info", Context.MODE_WORLD_READABLE);
		map.put("host", prefs.getString("host", ""));
		map.put("port", prefs.getString("port", ""));
		return map;

	}

	static public void writeDestinationInfo(Context context, String host,
			String port) {

		SharedPreferences destinationInfo = context.getSharedPreferences(
				"destination_info", Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = destinationInfo.edit();
		editor.putString("host", host);
		editor.putString("port", port);
		editor.commit();
	}

	static public HashMap<String, String> getProxyMap(Context context) {

		HashMap<String, String> map = new HashMap<String, String>();
		SharedPreferences prefs = context.getSharedPreferences("proxy_info",
				Context.MODE_WORLD_READABLE);
		map.put("proxyHost", prefs.getString("proxyHost", ""));
		map.put("proxyPort", prefs.getString("proxyPort", ""));
		return map;

	}

	static public void writeProxyInfo(Context context, String host, String port) {

		SharedPreferences destinationInfo = context.getSharedPreferences(
				"proxy_info", Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = destinationInfo.edit();
		editor.putString("proxyHost", host);
		editor.putString("proxyPort", port);
		editor.commit();
	}

	static public String getDeviceID(Context context) {
		String deviceID = android.provider.Settings.Secure.getString(
				context.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		return deviceID;
	}

	static public long getTimeMilliseconds() {
		Calendar current = Calendar.getInstance();
		return current.getTimeInMillis();
	}

	static public boolean isDestinationInfoEmpty(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				"destination_info", Context.MODE_WORLD_READABLE);
		return (prefs.getString("host", "").isEmpty() || prefs.getString(
				"port", "").isEmpty());
	}

	public static String mergeArrayList(ArrayList<String> list) {
		StringBuilder listString = new StringBuilder();

		for (String s : list)
			listString.append(s + " ");
		return listString.toString();
	}
}
