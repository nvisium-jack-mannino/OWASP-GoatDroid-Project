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
package org.owasp.goatdroid.gui.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.owasp.goatdroid.gui.Constants;
import org.owasp.goatdroid.gui.Utils;
import org.owasp.goatdroid.gui.exception.CorruptConfigException;

public class Config {

	static String path;

	public Config() {

		Config.setPath(Utils.getCurrentPath() + Constants.CONFIG);
	}

	static public HashMap<String, String> readConfig()
			throws FileNotFoundException, IOException, CorruptConfigException {

		HashMap<String, String> config = new HashMap<String, String>();

		BufferedReader buffer = generateBufferedReader(getPath());
		String line;
		while ((line = buffer.readLine()) != null) {

			if (line.startsWith("sdk_path=")) {

				String[] splitLine = line.split("sdk_path=");
				if (splitLine.length > 1)
					config.put("sdk_path", splitLine[1]);
				else
					config.put("sdk_path", "");
			}

			else if (line.startsWith("devices_path=")) {

				String[] splitLine = line.split("devices_path=");
				if (splitLine.length > 1)
					config.put("devices_path", splitLine[1]);
				else
					config.put("devices_path", "");
			}

			else if (line.startsWith("proxy_host=")) {

				String[] splitLine = line.split("proxy_host=");
				if (splitLine.length > 1)
					config.put("proxy_host", splitLine[1]);
				else
					config.put("proxy_host", "");
			}

			else if (line.startsWith("proxy_port=")) {

				String[] splitLine = line.split("proxy_port=");
				if (splitLine.length > 1)
					config.put("proxy_port", splitLine[1]);
				else
					config.put("proxy_port", "");
			}

			else if (line.startsWith("web_service_http_port=")) {

				String[] splitLine = line.split("web_service_http_port=");
				if (splitLine.length > 1)
					config.put("web_service_http_port", splitLine[1]);
				else
					config.put("web_service_http_port", "");
			}

			else if (line.startsWith("web_service_https_port=")) {

				String[] splitLine = line.split("web_service_https_port=");
				if (splitLine.length > 1)
					config.put("web_service_https_port", splitLine[1]);
				else
					config.put("web_service_https_port", "");
			}

			else if (line.startsWith("emulator_screen_size=")) {

				String[] splitLine = line.split("emulator_screen_size=");
				if (splitLine.length > 1)
					config.put("emulator_screen_size", splitLine[1]);
				else
					config.put("emulator_screen_size", "");
			}

			else
				throw new CorruptConfigException("Configuration is corrupt");

		}
		// If we don't have every config option, something is weird
		if (config.size() == 7)
			return config;
		else
			throw new CorruptConfigException("Configuration is corrupt");
	}

	static public void updateAndroidSettings(String sdkPath,
			String devicesPath, String proxyHost, String proxyPort,
			String emulatorScreenSize) throws FileNotFoundException,
			IOException, CorruptConfigException {

		HashMap<String, String> settings = readConfig();
		settings.put("sdk_path", sdkPath);
		settings.put("devices_path", devicesPath);
		settings.put("proxy_host", proxyHost);
		settings.put("proxy_port", proxyPort);
		settings.put("emulator_screen_size", emulatorScreenSize);
		writeSettings(settings);

	}

	static public void updateWebServiceSettings(String httpPort,
			String httpsPort) throws FileNotFoundException, IOException,
			CorruptConfigException {

		HashMap<String, String> settings = readConfig();
		settings.put("web_service_http_port", httpPort);
		settings.put("web_service_https_port", httpsPort);
		writeSettings(settings);
	}

	static public String getSDKPath() throws FileNotFoundException,
			IOException, CorruptConfigException {

		BufferedReader buffer = generateBufferedReader(getPath());
		String line;
		String emulatorPath = null;

		while ((line = buffer.readLine()) != null) {

			if (line.startsWith("sdk_path=")) {

				String[] splitLine = line.split("sdk_path=");
				if (splitLine.length > 1)
					emulatorPath = splitLine[1];
				else
					emulatorPath = "";
			}
		}

		// If we don't have every config option, something is weird
		if (emulatorPath != null)
			return emulatorPath;
		else
			throw new CorruptConfigException("Configuration is corrupt");
	}

	static public String getVirtualDevicesPath() throws FileNotFoundException,
			IOException, CorruptConfigException {

		BufferedReader buffer = generateBufferedReader(getPath());
		String line;
		String devicesPath = null;

		while ((line = buffer.readLine()) != null) {

			if (line.startsWith("devices_path=")) {

				String[] splitLine = line.split("devices_path=");
				if (splitLine.length > 1)
					devicesPath = splitLine[1];
				else
					devicesPath = "";
			}
		}

		// If we don't have every config option, something is weird
		if (devicesPath != null)
			return devicesPath;
		else
			throw new CorruptConfigException("Configuration is corrupt");
	}

	static public int getAndroidEmulatorScreenSize()
			throws FileNotFoundException, IOException, CorruptConfigException {

		BufferedReader buffer = generateBufferedReader(getPath());
		String line;
		String screenSize = null;

		while ((line = buffer.readLine()) != null) {

			if (line.startsWith("emulator_screen_size=")) {

				String[] splitLine = line.split("emulator_screen_size=");
				if (splitLine.length > 1)
					screenSize = splitLine[1];
				else
					screenSize = "0";
			}
		}

		// If we don't have every config option, something is weird
		if (screenSize != null)
			return Integer.parseInt(screenSize);
		else
			throw new CorruptConfigException("Configuration is corrupt");
	}

	static public String getWebServiceHTTPPort() throws FileNotFoundException,
			IOException, CorruptConfigException {

		BufferedReader buffer = generateBufferedReader(getPath());
		String line;
		String httpPort = null;

		while ((line = buffer.readLine()) != null) {

			if (line.startsWith("web_service_http_port=")) {

				String[] splitLine = line.split("web_service_http_port=");
				if (splitLine.length > 1)
					httpPort = splitLine[1];
				else
					httpPort = "";
			}
		}

		// If we don't have every config option, something is weird
		if (httpPort != null)
			return httpPort;
		else
			throw new CorruptConfigException("Configuration is corrupt");
	}

	static public String getWebServiceHTTPSPort() throws FileNotFoundException,
			IOException, CorruptConfigException {

		BufferedReader buffer = generateBufferedReader(getPath());
		String line;
		String httpsPort = null;

		while ((line = buffer.readLine()) != null) {

			if (line.startsWith("web_service_https_port=")) {

				String[] splitLine = line.split("web_service_https_port=");
				if (splitLine.length > 1)
					httpsPort = splitLine[1];
				else
					httpsPort = "";
			}
		}

		// If we don't have every config option, something is weird
		if (httpsPort != null)
			return httpsPort;
		else
			throw new CorruptConfigException("Configuration is corrupt");
	}

	static public void writeSettings(HashMap<String, String> settings)
			throws IOException {

		Map<String, String> map = settings;
		Iterator it = map.entrySet().iterator();
		String config = "";
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
					.next();
			config += pairs.getKey() + "=" + pairs.getValue() + "\n";
		}
		writeTextFile(config);
	}

	static public void writeTextFile(String text) throws IOException {
		FileWriter writer = new FileWriter(getPath());
		PrintWriter printWriter = new PrintWriter(writer);
		printWriter.print(text);
		printWriter.close();

	}

	static public BufferedReader generateBufferedReader(String fileName)
			throws FileNotFoundException {

		FileReader fileReader = openFile(fileName);
		BufferedReader buffer = new BufferedReader(fileReader);

		return buffer;
	}

	static public FileReader openFile(String fileName)
			throws FileNotFoundException {

		FileReader fileStream;
		File file = new File(fileName);
		fileStream = new FileReader(file);
		return fileStream;
	}

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		Config.path = path;
	}
}
