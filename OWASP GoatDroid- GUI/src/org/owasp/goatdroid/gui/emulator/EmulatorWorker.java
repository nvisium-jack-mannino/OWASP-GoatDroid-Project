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

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.owasp.goatdroid.gui.Constants;
import org.owasp.goatdroid.gui.Utils;
import org.owasp.goatdroid.gui.config.Config;
import org.owasp.goatdroid.gui.exception.CorruptConfigException;

public class EmulatorWorker extends SwingWorker<String, EmulatorResult> {

	String action;
	String deviceName;
	String deviceSerial;
	String appPath;
	static String slash;
	static String sdkPath;
	static String operatingSystem;
	Component component;

	public EmulatorWorker(String action, String deviceName,
			String deviceSerial, String appPath, Component component)
			throws FileNotFoundException, IOException, CorruptConfigException {

		EmulatorWorker.setSlash(Utils.getFileSeparator());
		String[] splitDeviceName = deviceName.split(getSlash());
		this.action = action;
		this.deviceName = splitDeviceName[splitDeviceName.length - 1];
		EmulatorWorker.setOperatingSystem(System.getProperty("os.name")
				.toLowerCase());
		sdkPath = Config.getSDKPath();
		this.deviceSerial = deviceSerial;
		this.appPath = appPath;
		this.component = component;
	}

	@Override
	protected String doInBackground() throws Exception {
		String result = "";
		try {
			if (action.equals("startEmulator"))
				result = startEmulator(deviceName);
			else if (action.equals("startAndroidSDKManager"))
				result = startAndroidSDKManager();
			else if (action.equals("pushAppOntoDevice")) {
				if (deviceSerial.isEmpty())
					result = pushAppOntoDevice(appPath);
				else
					result = pushAppOntoDevice(appPath, deviceSerial);
			}
		} catch (FileNotFoundException e) {
			result = Constants.UNEXPECTED_ERROR;
		} catch (IOException e) {
			result = Constants.UNEXPECTED_ERROR;
		} catch (CorruptConfigException e) {
			result = Constants.CORRUPT_CONFIG;
		}
		publish(new EmulatorResult(result));
		return result;
	}

	@Override
	protected void process(List<EmulatorResult> result) {
		showMessageDialog(result.get(0).getMessage());
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(component, message);
	}

	static public String startEmulator(String deviceName)
			throws FileNotFoundException, IOException, CorruptConfigException {
		HashMap<String, String> settings = Config.readConfig();
		String emulator = settings.get("sdk_path") + getSlash() + "tools"
				+ getSlash();
		if (getOperatingSystem().startsWith("windows"))
			emulator += "emulator-arm.exe";
		else
			emulator += "emulator";
		CommandLine cmdLine = new CommandLine(emulator);
		// If the Windows path has spaces, we don't even want to deal with it
		// will ignore parameters, known issue
		if (getOperatingSystem().startsWith("windows")) {
			cmdLine.addArgument("@"
					+ deviceName.toLowerCase().replace(".avd", ""));
			cmdLine.addArgument("-scale");
			if (Config.getAndroidEmulatorScreenSize() == 0)
				cmdLine.addArgument("1");
			else if (Config.getAndroidEmulatorScreenSize() > 3)
				cmdLine.addArgument("3");
			else
				cmdLine.addArgument(Integer.toString(Config
						.getAndroidEmulatorScreenSize()));
		} else {
			cmdLine.addArgument("-avd", false);
			cmdLine.addArgument(deviceName.toLowerCase().replace(".avd", ""),
					false);
			cmdLine.addArgument("-scale");
			if (Config.getAndroidEmulatorScreenSize() < 1)
				cmdLine.addArgument("1");
			else if (Config.getAndroidEmulatorScreenSize() > 3)
				cmdLine.addArgument("3");
			else
				cmdLine.addArgument(Integer.toString(Config
						.getAndroidEmulatorScreenSize()));
		}
		if (!(settings.get("proxy_host").equals("") || settings.get(
				"proxy_port").equals(""))) {
			cmdLine.addArgument("-http-proxy");
			cmdLine.addArgument("http://" + settings.get("proxy_host") + ":"
					+ settings.get("proxy_port"));
		}
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		DefaultExecutor executor = new DefaultExecutor();
		try {
			executor.setStreamHandler(psh);
			executor.execute(cmdLine);
			return Constants.SUCCESS;
		} catch (ExecuteException e) {
			return stdout.toString();
		} catch (IOException e) {
			return Constants.INVALID_SDK_PATH;
		}
	}

	static public String startAndroidSDKManager() throws FileNotFoundException,
			IOException, CorruptConfigException {

		HashMap<String, String> settings = Config.readConfig();
		String emulator = "";
		if (getOperatingSystem().startsWith("windows"))
			if (Emulator.doesToolExist("", "AVD Manager.exe"))
				emulator = settings.get("sdk_path") + getSlash()
						+ "AVD Manager.exe";
			else
				emulator = settings.get("sdk_path") + getSlash() + "tools"
						+ getSlash() + "android";
		else
			emulator = settings.get("sdk_path") + getSlash() + "tools"
					+ getSlash() + "android";
		CommandLine cmdLine = new CommandLine(emulator);

		DefaultExecutor executor = new DefaultExecutor();
		try {
			executor.execute(cmdLine);
			return Constants.SUCCESS;
		} catch (ExecuteException e) {
			return Constants.UNEXPECTED_ERROR;
		} catch (IOException e) {
			return Constants.UNEXPECTED_ERROR;
		}
	}

	static public String pushAppOntoDevice(String appPath) {

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		CommandLine cmdLine = new CommandLine(sdkPath + getSlash()
				+ "platform-tools" + getSlash() + "adb");
		cmdLine.addArgument("install");
		cmdLine.addArgument(appPath, false);
		DefaultExecutor executor = new DefaultExecutor();
		try {
			executor.setStreamHandler(psh);
			executor.execute(cmdLine);
			return stdout.toString();
		} catch (ExecuteException e) {
			return Constants.UNEXPECTED_ERROR;
		} catch (IOException e) {
			return Constants.UNEXPECTED_ERROR;
		}
	}

	static public String pushAppOntoDevice(String appPath, String deviceSerial) {

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		CommandLine cmdLine = new CommandLine(sdkPath + getSlash()
				+ "platform-tools" + getSlash() + "adb");
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceSerial", deviceSerial);
		cmdLine.addArgument("-s", false);
		cmdLine.addArgument("${deviceSerial}", false);
		cmdLine.addArgument("install", false);
		cmdLine.addArgument(appPath, false);
		cmdLine.setSubstitutionMap(map);
		DefaultExecutor executor = new DefaultExecutor();
		try {
			executor.setStreamHandler(psh);
			executor.execute(cmdLine);
			return stdout.toString();
		} catch (ExecuteException e) {
			return Constants.UNEXPECTED_ERROR;
		} catch (IOException e) {
			return Constants.UNEXPECTED_ERROR;
		}
	}

	public static String getSlash() {
		return slash;
	}

	public static void setSlash(String slash) {
		EmulatorWorker.slash = slash;
	}

	public static String getOperatingSystem() {
		return operatingSystem;
	}

	public static void setOperatingSystem(String operatingSystem) {
		EmulatorWorker.operatingSystem = operatingSystem;
	}
}
