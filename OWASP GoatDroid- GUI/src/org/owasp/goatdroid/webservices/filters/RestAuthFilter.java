package org.owasp.goatdroid.webservices.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

public class RestAuthFilter extends GenericFilterBean {

	AuthenticationManager authenticationManager;
	AuthenticationEntryPoint authenticationEntryPoint;

	public RestAuthFilter(AuthenticationManager authenticationManager) {
		authenticationEntryPoint = new BasicAuthenticationEntryPoint();
		((BasicAuthenticationEntryPoint) authenticationEntryPoint)
				.setRealmName("stuff goes here");
	}

	public RestAuthFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationManager = authenticationManager;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

	}

	public String[] decodeHeader(String authorization) {
		try {
			byte[] decoded = Base64.decode(authorization.substring(6).getBytes(
					"UTF-8"));
			String credentials = new String(decoded);
			return credentials.split(":");
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

}
