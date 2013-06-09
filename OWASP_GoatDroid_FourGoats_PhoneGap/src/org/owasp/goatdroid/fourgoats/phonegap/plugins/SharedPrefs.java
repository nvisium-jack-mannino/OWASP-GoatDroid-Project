package org.owasp.goatdroid.fourgoats.phonegap.plugins;

import java.util.HashMap;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs extends CordovaPlugin {

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		if (action.equals("getCredentials")) {

		} else if (action.equals("saveCredentials")) {

		} else if (action.equals("getDestinationInfo")) {

		} else if (action.equals("getProxyInfo")) {

		} else if (action.equals("writeDestinationInfo")) {

		} else if (action.equals("writeProxyInfo")) {

		}
		return false;
	}

	String getDestinationInfo() {

		String destinationInfo = "";
		SharedPreferences prefs = this.cordova.getActivity()
				.getSharedPreferences("destination_info",
						Context.MODE_WORLD_READABLE);
		destinationInfo += prefs.getString("host", "");
		destinationInfo += ":";
		destinationInfo += prefs.getString("port", "");
		return destinationInfo;
	}

	HashMap<String, String> getDestinationInfoMap() {

		HashMap<String, String> map = new HashMap<String, String>();
		SharedPreferences prefs = this.cordova.getActivity()
				.getSharedPreferences("destination_info",
						Context.MODE_WORLD_READABLE);
		map.put("host", prefs.getString("host", ""));
		map.put("port", prefs.getString("port", ""));
		return map;
	}

	void setDestinationInfo(String host, String port) {

		SharedPreferences destinationInfo = this.cordova.getActivity()
				.getSharedPreferences("destination_info",
						Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = destinationInfo.edit();
		editor.putString("host", host);
		editor.putString("port", port);
		editor.commit();
	}

	HashMap<String, String> getProxyMap() {

		HashMap<String, String> map = new HashMap<String, String>();
		SharedPreferences prefs = this.cordova
				.getActivity()
				.getSharedPreferences("proxy_info", Context.MODE_WORLD_READABLE);
		map.put("proxyHost", prefs.getString("proxyHost", ""));
		map.put("proxyPort", prefs.getString("proxyPort", ""));
		return map;

	}

	void setProxyInfo(String host, String port) {

		SharedPreferences destinationInfo = this.cordova
				.getActivity()
				.getSharedPreferences("proxy_info", Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = destinationInfo.edit();
		editor.putString("proxyHost", host);
		editor.putString("proxyPort", port);
		editor.commit();
	}
}
