package org.owasp.goatdroid.fourgoats.rest.login;

import javax.net.ssl.HostnameVerifier;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class MySimpleClientHttpRequestFactory extends
		SimpleClientHttpRequestFactory {
	private final NullHostnameVerifier verifier;

	public MySimpleClientHttpRequestFactory(final NullHostnameVerifier verifier) {
		this.verifier = verifier;
	}

}