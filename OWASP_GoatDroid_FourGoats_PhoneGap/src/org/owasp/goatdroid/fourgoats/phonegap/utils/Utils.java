package org.owasp.goatdroid.fourgoats.phonegap.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.owasp.goatdroid.fourgoats.phonegap.constants.AppConstants;

public class Utils {

	static public String getCurrentDateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new java.text.SimpleDateFormat(
				AppConstants.DATE_FORMAT);
		return df.format(cal.getTime());
	}
}
