package org.owasp.goatdroid.fourgoats.phonegap.plugins;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class IntentGenerator extends CordovaPlugin {

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		if (action.equals("startService")) {
			return true;
		} else if (action.equals("startActivity")) {
			return true;
		} else if (action.equals("sendBroadcast")) {
			return true;
		}
		return false;
	}
}
