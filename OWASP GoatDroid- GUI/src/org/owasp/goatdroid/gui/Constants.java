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
package org.owasp.goatdroid.gui;

public class Constants {

	public static final String SETTINGS_UPDATED_SUCCESS = "Your settings have been updated";
	public static final String SUCCESS = "success";
	public static final String SETTINGS_UPDATE_FAILED = "Settings could not be updated";
	public static final String APP_PUSH_FAILED = "Couldn't find your app";
	public static final String CORRUPT_CONFIG = "Your configuration may be corrupt";
	public static final String EMULATOR_COMMS_FAILURE = "Could Not Connect To The Emulator";
	public static final String PUSHING_APP = "Now pushing the app onto your device....";
	public static final String SELECT_AN_APP = "Please select a lesson or app first";
	public static final String SPECIFY_SDK_LOCATION = "SDK Location Not Set.  Configure -> Edit -> Android";
	public static final String APPS = "Apps";
	public static final String TOP10 = "Top 10";
	public static final String LESSONS = "Lessons";
	public static final String GOATDROID_APPS_DIRECTORY = "goatdroid_apps";
	public static final String TOP10_DIRECTORY = "top10";
	public static final String LESSONS_DIRECTORY = "lessons";
	public static final String DATABASE_DIRECTORY = "dbs";
	public static final String CONFIG = "config";
	public static final String NO_WEB_PORTS_CONFIGURED = "You must configure an HTTP and HTTPS port prior to launching web services";
	public static final String IP_FORMAT_ERROR = "IP address must be in the format xxx.xxx.xxx.xxx";
	public static final String PORT_FORMAT_ERROR = "Port must be 1-65535";
	public static final String EMULATOR_SCREEN_SIZE_ERROR = "Screen size must be 0-3";
	public static final String PHONE_NUMBER_ERROR = "Phone number must be 10 digits only";
	public static final String SMS_MESSAGE_ERROR = "Message must be 1-140 characters";
	public static final String LATITUDE_ERROR = "Latitude must be formatted as +-xxx.xxxxx";
	public static final String LONGITUDE_ERROR = "Longitude must be formatted as +-xxx.xxxxx";
	public static final String INVALID_SDK_PATH = "The tool could not be found. Please check your SDK path.";
	public static final String INVALID_AVD_PATH = "The AVD path you've specified is incorrect or you have not created any virtual devices yet. Please check your settings.";
	public static final String UNEXPECTED_ERROR = "This shouldn't have happened. Please open a bug ticket.\n\n"
			+ "https://github.com/jackMannino/OWASP-GoatDroid-Project/issues";
	public static final String NO_ANDROID_DEVICES_AVAILABLE = "No Android devices were found. Please attach a device or start an "
			+ "emulator instance.";
	public static final String NO_ANDROID_VIRTUAL_DEVICES = "No Android virtual devices were found. Please create one first using "
			+ "the Android SDK Manager";
	// Regex
	public static final String IP_REGEX = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
	public static final String PORT_REGEX = "\\b([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])\\b";
	public static final String EMULATOR_SCREEN_SIZE_REGEX = "\\b[0-3]\\b";
	public static final String PHONE_NUMBER_REGEX = "\\b[0-9]{10}\\b";
	public static final String LAT_LONG_REGEX_WITH_NEGATIVE = "-\\d{1,3}.\\d{5,20}";
	public static final String LAT_LONG_REGEX = "\\d{1,3}.\\d{5,20}";
}
