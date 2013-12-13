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
package org.owasp.goatdroid.tools;

import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;

public class Emulator {

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
}
