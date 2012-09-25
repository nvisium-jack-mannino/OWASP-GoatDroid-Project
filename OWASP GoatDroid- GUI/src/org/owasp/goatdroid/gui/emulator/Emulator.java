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
package org.owasp.goatdroid.gui.emulator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTree;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.owasp.goatdroid.gui.ResourceTreeBuilder;
import org.owasp.goatdroid.gui.Utils;
import org.owasp.goatdroid.gui.config.Config;
import org.owasp.goatdroid.gui.exception.CorruptConfigException;

public class Emulator {

	static Config config;

	public Emulator() throws FileNotFoundException, IOException,
			CorruptConfigException {

	}

	static public String getAppPath(JTree jTree, boolean useViewResource,
			String appName, String typeDirectory) {

		String directoryPath;
		if (useViewResource)
			directoryPath = ResourceTreeBuilder.getCurrentNodeParent(jTree)
					+ getSlash() + "android_app";
		else
			directoryPath = ResourceTreeBuilder.buildAPKPath(appName);
		File directory = new File(directoryPath);

		try {
			File[] entries = directory.listFiles();
			// Go over entries
			for (File entry : entries) {
				if (entry.isFile() && entry.toString().endsWith(".apk")) {
					return entry.toString();
				}
			}
		} catch (NullPointerException e) {
			e.getMessage();
		}
		// If we get here, we lost
		return "";
	}

	static public void sendLocation(String latitude, String longitude)
			throws SocketException, IOException {

		TelnetClient telnet = new TelnetClient();
		telnet.setDefaultPort(5554);
		telnet.connect("localhost");
		PrintStream outStream = new PrintStream(telnet.getOutputStream());
		// geo fix takes longitude/latitude, in that order
		outStream.println("geo fix " + longitude + " " + latitude);
		outStream.flush();
	}

	static public void sendSMSToEmulator(String phoneNumber, String message)
			throws SocketException, IOException {

		TelnetClient telnet = new TelnetClient();
		telnet.setDefaultPort(5554);
		telnet.connect("localhost");
		PrintStream outStream = new PrintStream(telnet.getOutputStream());
		outStream.println("sms send " + phoneNumber + " " + message);
		outStream.flush();

	}

	static public void callEmulator(String phoneNumber) throws SocketException,
			IOException {

		TelnetClient telnet = new TelnetClient();
		telnet.setDefaultPort(5554);
		telnet.connect("localhost");
		PrintStream outStream = new PrintStream(telnet.getOutputStream());
		outStream.println("gsm call " + phoneNumber);
		outStream.flush();

	}

	static public ArrayList<String> getVirtualDevices(String virtualDevicePath) {

		ArrayList<String> virtualDevices = new ArrayList<String>();
		File deviceFolder = new File(virtualDevicePath);

		for (File device : deviceFolder.listFiles()) {
			if (device.toString().endsWith(".avd") && device.isDirectory()) {
				if (device.toString().contains(getSlash())) {
					String[] splitDeviceString = device.toString().split(
							getSlash());
					virtualDevices
							.add(splitDeviceString[splitDeviceString.length - 1]);
				} else
					virtualDevices.add(device.toString());
			}
		}
		return virtualDevices;

	}

	static public boolean isPathValid(String virtualDevicePath) {
		try {
			File deviceFolder = new File(virtualDevicePath);
			for (File device : deviceFolder.listFiles()) {
				if (device.toString().endsWith(".avd") && device.isDirectory()) {
					return true;
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	static public boolean doesAndroidManagerToolExist() {
		if (Utils.getOS().startsWith("windows")) {
			if (!doesToolExist("tools", "android.exe"))
				return doesToolExist("", "AVD Manager.exe");
			else
				return true;
		} else {
			return doesToolExist("tools", "android");
		}
	}

	static public boolean doesToolExist(String directory, String toolName) {

		File file;
		if (directory.isEmpty())
			file = new File(getSdkPath() + getSlash() + toolName);
		else
			file = new File(getSdkPath() + getSlash() + directory + getSlash()
					+ toolName);
		if (file.exists())
			return true;
		else
			return false;
	}

	static public boolean isAndroidDeviceAvailable() {

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		try {

			CommandLine cmdLine = new CommandLine(Config.getSDKPath()
					+ getSlash() + "platform-tools" + getSlash() + "adb");
			cmdLine.addArgument("devices", false);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(psh);
			executor.execute(cmdLine);
			String stringy = stdout.toString();
			stdout.close();
			/*
			 * Shell command response will be 27 characters in length if there
			 * are no devices currently attached.
			 * 
			 * Output is: List of devices attached \n\n
			 */
			if (stringy.length() > 27)
				return true;
			else
				return false;
		} catch (CorruptConfigException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	static public String[] getAvailableAndroidDevices() {

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		try {

			CommandLine cmdLine = new CommandLine(Config.getSDKPath()
					+ getSlash() + "platform-tools" + getSlash() + "adb");
			cmdLine.addArgument("devices", false);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(psh);
			executor.execute(cmdLine);
			String output = stdout.toString();
			/*
			 * Shell command response will be 27 characters in length if there
			 * are no devices currently attached.
			 * 
			 * Output is: List of devices attached \n\n
			 */
			if (output.length() > 27) {
				List<String> devices = new ArrayList<String>(
						Arrays.asList(output.split("\n")));
				/*
				 * This removes the first line.. We don't care about the first
				 * one [0] because that's just the first line (List of devices
				 * attached \n)
				 */
				devices.remove(0);
				for (int count = 0; count < devices.size(); count++) {
					devices.set(count, devices.get(count)
							.replaceAll("\t.*", "").trim());
				}
				return devices.toArray(new String[devices.size()]);
				/*
				 * If there were no devices, return an empty list
				 */
			} else {
				return new String[0];
			}
		}

		catch (CorruptConfigException e) {
			return new String[0];
		} catch (IOException e) {
			return new String[0];
		}
	}

	public static String getSdkPath() {
		String sdkPath = "";
		try {
			sdkPath = Config.getSDKPath();
		} catch (FileNotFoundException e) {
		} catch (CorruptConfigException e) {
		} catch (IOException e) {
		}
		return sdkPath;
	}

	public static String getSlash() {
		return Utils.getFileSeparator();
	}
}
