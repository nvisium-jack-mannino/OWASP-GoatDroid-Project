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
package org.owasp.goatdroid.fourgoats.javascriptinterfaces;

import org.owasp.goatdroid.fourgoats.activities.GenericWebViewActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WebViewJSInterface implements Cloneable {

	Context mContext;

	public WebViewJSInterface(Context context) {

		mContext = context;
	}

	public void launchWebView(String url) {

		Intent intent = new Intent(mContext, GenericWebViewActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		intent.putExtras(bundle);
		mContext.startActivity(intent);
	}
}
