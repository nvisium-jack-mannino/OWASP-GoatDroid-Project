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

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.owasp.goatdroid.gui.Utils;
import org.owasp.goatdroid.webservice.fourgoats.interceptors.FGAuthenticationInterceptor;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

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
		/*
		 * This loads the web service
		 */
		DispatcherServlet dispatcher = new DispatcherServlet();
		dispatcher
				.setContextConfigLocation("classpath:/spring-context.xml");
		ServletHolder sh = new ServletHolder(dispatcher);
		server = new Server(10000);
		ServletContextHandler context = new ServletContextHandler(server, "/");
		context.setAliases(true);
		context.addServlet(sh, "/*");
		server.setHandler(context);
		if (isHTTPS) {
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
			} catch (InstantiationException e) {
				e.getMessage();
			}
		} else {
			server.start();
		}
		/*
		 * This loads the front-end website
		 */
		Server webServer = new Server(12000);
		WebAppContext webContext = new WebAppContext();
		webContext.setDescriptor("webapps/goatdroid/WEB-INF/web.xml");
		webContext.setResourceBase("webapps/goatdroid/");
		webContext.setContextPath("/");
		webContext.setParentLoaderPriority(true);
		webServer.setHandler(webContext);
		webServer.start();
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

		keyStorePath = "keystore/";
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
