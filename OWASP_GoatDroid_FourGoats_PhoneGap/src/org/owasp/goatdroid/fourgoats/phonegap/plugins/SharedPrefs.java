package org.owasp.goatdroid.fourgoats.phonegap.plugins;

import java.util.HashMap;
import java.util.Map;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs extends CordovaPlugin {

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		if (action.equals("getCredentials")) {
			return true;
		} else if (action.equals("saveCredentials")) {
			return true;
		} else if (action.equals("getDestinationInfo")) {
			callbackContext.sendPluginResult(getDestinationInfo());
			return true;
		} else if (action.equals("setDestinationInfo")) {
			callbackContext.sendPluginResult(setDestinationInfo(args));
			return true;
		}
		return false;
	}

	PluginResult getDestinationInfo() {

		SharedPreferences prefs = this.cordova.getActivity()
				.getSharedPreferences("destination_info",
						Context.MODE_WORLD_READABLE);

		Map<String, String> destinationInfo = new HashMap<String, String>();
		destinationInfo.put("serverIp", prefs.getString("host", ""));
		destinationInfo.put("serverPort", prefs.getString("port", ""));
		return new PluginResult(Status.OK,
				new JSONObject(destinationInfo).toString());
	}

	PluginResult setDestinationInfo(JSONArray args) {

		SharedPreferences destinationInfo = this.cordova.getActivity()
				.getSharedPreferences("destination_info",
						Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = destinationInfo.edit();
		try {
			editor.putString("host", args.getString(0));
			editor.putString("port", args.getString(1));
			editor.commit();
			return new PluginResult(Status.OK);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
			return new PluginResult(Status.ERROR);
		}
	}
}
