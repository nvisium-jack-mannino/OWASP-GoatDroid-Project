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
package org.owasp.goatdroid.gui.webservice;

import javax.swing.JTree;
import org.owasp.goatdroid.gui.ResourceTreeBuilder;
import org.owasp.goatdroid.gui.Utils;

public class WebService {

	private static WebServiceLauncher service;
	private static String serviceName;
	boolean isServiceRunning;
	private static String slash;

	public WebService(JTree jTree) {

		WebService.setSlash(Utils.getFileSeparator());
		WebService.setServiceName(getServiceName(jTree));
		WebService.setService(new WebServiceLauncher(getServiceName()));
	}

	static public String getServiceName(JTree jTree) {
		ResourceTreeBuilder lessonTree = new ResourceTreeBuilder(
				"goatdroid_apps", "");

		@SuppressWarnings("static-access")
		String[] directories = lessonTree.getCurrentNodeParent(jTree).split(
				getSlash());
		// offset of our desired value
		return directories[directories.length - 1].toLowerCase();
	}

	static public void startService(boolean isHTTPS) throws Exception {

		WebServiceLauncher.startService(isHTTPS);
	}

	static public void stopService() throws Exception {
		WebServiceLauncher.stopService();

	}

	static public boolean doesAppMatchRunningService(JTree newJTree) {

		String newServiceName = getServiceName(newJTree);

		// If it isn't running, we stop here
		if (!WebServiceLauncher.isServiceRunning())
			return false;

		newServiceName = newServiceName.replace(" ", "");
		if (newServiceName.equals(getServiceName()))
			return true;
		else
			return false;
	}

	static public boolean isServiceRunning() {

		return WebServiceLauncher.isServiceRunning();
	}

	public static String getServiceName() {
		return serviceName;
	}

	public static void setServiceName(String serviceName) {
		WebService.serviceName = serviceName;
	}

	public static String getSlash() {
		return slash;
	}

	public static void setSlash(String slash) {
		WebService.slash = slash;
	}

	public static WebServiceLauncher getService() {
		return service;
	}

	public static void setService(WebServiceLauncher service) {
		WebService.service = service;
	}
}
