package org.owasp.goatdroid.webservice.authentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthToken(String key, String credentials) {
		super(key, credentials);
	}

	public AuthToken(String key, String credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(key, credentials, authorities);
	}

	public String getKey() {
		return (String) super.getPrincipal();
	}

	public String getCredentials() {
		return (String) super.getCredentials();
	}
}
