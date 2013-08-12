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
 * @author Walter Tighzert
 * @created 2012
 */
package org.owasp.goatdroid.fourgoats.rest.login;

import java.net.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.requestresponse.AuthenticatedRestClient;
import org.owasp.goatdroid.fourgoats.requestresponse.RequestMethod;
import org.owasp.goatdroid.fourgoats.requestresponse.RestClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;

public class LoginRequest {

	Context context;
	String destinationInfo;

	public LoginRequest(Context context) {

		this.context = context;
		destinationInfo = Utils.getDestinationInfo(context);
	}

	public boolean isSessionValid(String sessionToken) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/fourgoats/api/v1/pub/login/check-session");
		client.AddHeader("Cookie", "SESS=" + sessionToken);
		client.Execute(RequestMethod.GET, context);
		return LoginResponse.isSuccess(client.getResponse());
	}

	public Login validateCredentials(String userName,
			String password) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/fourgoats/api/v1/pub/login/authenticate");
		client.AddParam("username", userName);
		client.AddParam("password", password);
		client.Execute(RequestMethod.POST, context);

		return LoginResponse.parseLoginResponse(client.getResponse());
	}

	public HashMap<String, String> validateCredentialsAPI(String userName,
			String password) throws Exception {

		RestClient client = new RestClient("https://" + destinationInfo
				+ "/fourgoats/api/v1/pub/login/validate-api");
		client.AddParam("username", userName);
		client.AddParam("password", password);
		client.Execute(RequestMethod.POST, context);

		return LoginResponse.parseAPILoginResponse(client.getResponse());
	}

	public HashMap<String, String> logOut(String sessionToken) throws Exception {

		AuthenticatedRestClient client = new AuthenticatedRestClient("https://"
				+ destinationInfo + "/fourgoats/api/v1/pub/login/sign-out");
		client.Execute(RequestMethod.POST, context);

		return LoginResponse.parseStatusAndErrors(client.getResponse());
	}

	public void testLogin() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "goatdroid");
		map.put("password", "goatdroid");
		HttpEntity<?> requestEntity = new HttpEntity<Object>(map,
				requestHeaders);
		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		/*
		 * KeyStore trustStore; try { trustStore =
		 * KeyStore.getInstance(KeyStore.getDefaultType());
		 * trustStore.load(null, null); restTemplate.setRequestFactory(new
		 * CustomSSLSocketFactory( trustStore)); } catch (KeyStoreException e1)
		 * { // TODO Auto-generated catch block e1.printStackTrace(); } catch
		 * (KeyManagementException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UnrecoverableKeyException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (java.security.cert.CertificateException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*try {
			ResponseEntity<Login[]> responseEntity = restTemplate
					.exchange(
							"http://192.168.1.123:10000/fourgoats/api/v1/pub/login/authenticate",
							HttpMethod.POST, requestEntity, Login[].class);
			Login[] login = responseEntity.getBody();
			login.getClass();
		} catch (RestClientException e) {
			e.getMessage();
		}*/
	}
}
