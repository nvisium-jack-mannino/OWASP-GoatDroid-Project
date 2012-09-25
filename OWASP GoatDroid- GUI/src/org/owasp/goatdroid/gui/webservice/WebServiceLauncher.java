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

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.owasp.goatdroid.gui.Utils;
import org.owasp.goatdroid.gui.config.Config;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class WebServiceLauncher {

	private static String serviceName;
	static Server server;
	static String keyStorePath;
	private static String slash;

	public WebServiceLauncher(String serviceName) {
		// remove spaces to match web resource formats
		WebServiceLauncher.serviceName = serviceName.replace(" ", "");
		WebServiceLauncher.setSlash(Utils.getFileSeparator());
		setKeyStore();
	}

	static public void startService(boolean isHTTPS) throws Exception {

		ServletHolder sh = new ServletHolder(ServletContainer.class);
		sh.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages",
				"org.owasp.goatdroid.webservice");
		server = new Server(Integer.parseInt(Config.getWebServiceHTTPSPort()));
		ServletContextHandler context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);
		context.setAliases(true);
		context.addServlet(sh, "/*");
		if (isHTTPS) {
			SslSocketConnector sslConnector = new SslSocketConnector();
			sslConnector.setPort(Integer.parseInt(Config
					.getWebServiceHTTPSPort()));
			sslConnector.setPassword("goatdroid");
			sslConnector.setKeyPassword("goatdroid");
			sslConnector.setTrustPassword("goatdroid");
			sslConnector.setKeystore(keyStorePath);
			SelectChannelConnector selectChannelConnector = new SelectChannelConnector();
			// selectChannelConnector.setPort(Integer.parseInt(config
			// .getWebServicePort()));
			server.setConnectors(new Connector[] { sslConnector,
					selectChannelConnector });
			server.start();
		} else {
			server.start();
		}
	}

	static public void stopService() throws Exception {
		server.stop();
	}

	static public boolean isServiceRunning() {

		if (server.getState().equals("STARTED"))
			return true;
		else
			return false;
	}

	private void setKeyStore() {

		keyStorePath = Utils.getCurrentPath() + "keystore";
	}

	public static String getSlash() {
		return slash;
	}

	public static void setSlash(String slash) {
		WebServiceLauncher.slash = slash;
	}

	public static String getServiceName() {
		return serviceName;
	}

	public static void setServiceName(String serviceName) {
		WebServiceLauncher.serviceName = serviceName;
	}
}
