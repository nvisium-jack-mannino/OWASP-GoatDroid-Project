package org.owasp.goatdroid.webservice.authentication;

import java.util.ArrayList;
import java.util.List;
import org.owasp.goatdroid.webservice.fourgoats.services.FGLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RestAuthenticationProvider implements AuthenticationProvider {

	FGLoginServiceImpl loginService;

	@Autowired
	public RestAuthenticationProvider(FGLoginServiceImpl loginService) {
		this.loginService = loginService;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		AuthToken restToken = (AuthToken) authentication;

		String key = restToken.getKey();
		String credentials = restToken.getCredentials();
		if (loginService.validateAuthToken(key, credentials)) {

		}
		if (!key.equals("jack") || !credentials.equals("jill")) {
			throw new BadCredentialsException("Enter a username and password");
		}

		return getAuthenticatedUser(key, credentials);
	}

	private Authentication getAuthenticatedUser(String key, String credentials) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new AuthToken(key, credentials, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
