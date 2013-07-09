package org.owasp.goatdroid.fourgoats.phonegap.plugins;

import java.util.HashMap;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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

	public Intent buildIntent(HashMap<String, Object> intentMap) {

		Intent intent = new Intent();

		if (intentMap.get("action") != null) {

			intent.setAction((String) intentMap.get("action"));
		}

		if (intentMap.get("data") != null) {

			intent.setData((Uri) intentMap.get("data"));
		}

		if (intentMap.get("category") != null) {

			intent.addCategory((String) intentMap.get("category"));
		}

		if (intentMap.get("type") != null) {

			intent.setType((String) intentMap.get("type"));

		}

		if (intentMap.get("component") != null) {

			intent.setComponent((ComponentName) intentMap.get("component"));

		}

		if (intentMap.get("extras") != null) {

			intent.putExtras((Bundle) intentMap.get("extras"));

		}

		if (intentMap.get("flags") != null) {

			intent.setFlags((Integer) intentMap.get("flags"));
		}

		return intent;
	}
}
