package org.owasp.goatdroid.fourgoats.misc;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLCertificateValidation {

	public static void disable() {
		try {
			SSLContext sslc = SSLContext.getInstance("TLS");
			TrustManager[] trustManagerArray = { new NullX509TrustManager() };
			sslc.init(null, trustManagerArray, null);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslc
					.getSocketFactory());
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class NullX509TrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			System.out.println();
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			System.out.println();
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}

	private static class NullHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

}