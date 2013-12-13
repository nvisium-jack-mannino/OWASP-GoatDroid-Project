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
package org.owasp.goatdroid.webservice.initializers;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebServiceLauncher {

	private static String serviceName;

	static String keyStorePath;

	public WebServiceLauncher(String serviceName) {
		// remove spaces to match web resource formats
		WebServiceLauncher.serviceName = serviceName.replace(" ", "");
		setKeyStore();
	}

	static public void startServices(boolean isHttps) throws Exception {
		startWebService(isHttps);
		startWebApp();
	}

	static void startWebService(boolean isHttps) {
		DispatcherServlet dispatcher = new DispatcherServlet();
		Server server;
		dispatcher
				.setContextConfigLocation("classpath:/spring-web-service-context.xml");
		ServletHolder sh = new ServletHolder(dispatcher);
		server = new Server(10000);
		ServletContextHandler context = new ServletContextHandler(server, "/");
		context.setAliases(true);
		context.addServlet(sh, "/*");
		server.setHandler(context);
		if (isHttps) {
			SslSocketConnector sslConnector = new SslSocketConnector();
			sslConnector.setPort(10000);
			sslConnector.setPassword("goatdroid");
			sslConnector.setKeyPassword("goatdroid");
			sslConnector.setTrustPassword("goatdroid");
			sslConnector.setKeystore("keystore/keystore");
			SelectChannelConnector selectChannelConnector = new SelectChannelConnector();
			server.setConnectors(new Connector[] { sslConnector,
					selectChannelConnector });
			try {
				server.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	static void startWebApp() {
		DispatcherServlet dispatcher = new DispatcherServlet();
		Server server;
		dispatcher
				.setContextConfigLocation("classpath:/spring-webapp-context.xml");
		ServletHolder sh = new ServletHolder(dispatcher);
		server = new Server(12000);
		ServletContextHandler context = new ServletContextHandler(server, "/");
		context.setAliases(true);
		context.addServlet(sh, "/*");
		context.setResourceBase("webapps/goatdroid/");
		server.setHandler(context);

		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setKeyStore() {

		keyStorePath = "keystore/";
	}

	public static String getServiceName() {
		return serviceName;
	}

	public static void setServiceName(String serviceName) {
		WebServiceLauncher.serviceName = serviceName;
	}

}
