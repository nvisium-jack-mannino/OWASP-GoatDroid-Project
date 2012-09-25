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

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Utils {

	static public String getCurrentPath() {

		String path;
		String slash = getFileSeparator();
		String testPath = Utils.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (testPath.endsWith(".jar")) {
			String[] pathSplit;
			if (Utils.getOS().startsWith("windows")) {
				testPath = testPath.replaceFirst("^/", "");
				pathSplit = testPath.split("/");
			} else
				pathSplit = testPath.split(slash);
			String updatedPath = "";
			for (int count = 0; count < pathSplit.length - 1; count++) {
				if (!pathSplit[count].endsWith(".jar"))
					updatedPath += pathSplit[count] + "/";
			}
			path = updatedPath;
		} else {
			if (Utils.getOS().startsWith("windows")) {
				path = Utils.class.getProtectionDomain().getCodeSource()
						.getLocation().getPath().replaceFirst("^/", "");
			} else
				path = Utils.class.getProtectionDomain().getCodeSource()
						.getLocation().getPath();
		}
		try {
			path = URLDecoder.decode(path, "UTF-8");
			return path;
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
			return "";
		}
	}

	static public String getFileSeparator() {

		if (System.getProperty("os.name").toLowerCase().startsWith("windows"))
			return File.separator + File.separator;
		else
			return File.separator;
	}

	static public String getOS() {
		return System.getProperty("os.name").toLowerCase();
	}
}
