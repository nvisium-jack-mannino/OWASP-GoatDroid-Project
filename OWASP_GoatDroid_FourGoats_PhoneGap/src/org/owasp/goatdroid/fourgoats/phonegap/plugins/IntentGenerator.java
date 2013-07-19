package org.owasp.goatdroid.fourgoats.phonegap.plugins;

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
			this.cordova.getActivity().startService(buildIntent(args));
			return true;
		} else if (action.equals("startActivity")) {
			return true;
		} else if (action.equals("sendBroadcast")) {
			return true;
		}
		return false;
	}

	public Intent buildIntent(JSONArray args) throws JSONException {

		Intent intent = new Intent();

		if (!args.get(0).equals(null))
			intent.setAction(args.getString(0));

		if (!args.get(1).equals(null))
			intent.setData((Uri) args.get(1));

		if (!args.get(2).equals(null))
			intent.addCategory((String) args.getString(2));

		if (!args.get(3).equals(null))
			intent.setType(args.getString(3));

		if (!args.get(4).equals(null)) {
			//intent.setComponent(new ComponentName(args.getString(4)));
		}
		if (!args.get(5).equals(null))
			intent.putExtras((Bundle) args.get(5));

		if (!args.get(6).equals(null))
			intent.setFlags(args.getInt(6));

		return intent;
	}
}
