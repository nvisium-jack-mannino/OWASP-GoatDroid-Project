package org.owasp.goatdroid.fourgoats.rest.login;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NullHostnameVerifier implements HostnameVerifier {
	@Override
	public boolean verify(final String hostname, final SSLSession session) {
		return true;
	}
}