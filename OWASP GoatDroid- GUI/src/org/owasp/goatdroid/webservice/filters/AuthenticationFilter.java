package org.owasp.goatdroid.webservice.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.owasp.goatdroid.webservice.fourgoats.services.FGLoginServiceImpl;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		/*
		 * First we check to ensure that the string is real Base64 and not
		 * garbage
		 */
		if (Base64.isBase64(httpServletRequest.getHeader("authorization")
				.getBytes())) {
			String authorizationString = new String(
					Base64.decode(httpServletRequest.getHeader("authorization")
							.getBytes()));
			/*
			 * We split the username and password on the first colon. Any colons
			 * encountered afterwards are considered part of the password.
			 */
			String userName = "";
			String token = "";
			boolean isColonFound = false;
			for (char ch : authorizationString.toCharArray()) {
				if (ch == ':')
					isColonFound = true;
				else if (!isColonFound)
					userName += ch;
				else
					token += ch;
			}

			/*
			 * Check to make sure we had a username and a token, neither can be
			 * blank
			 */

			/*
			 * Now, we check to ensure the authentication token is valid for the
			 * user account
			 */
			FGLoginServiceImpl loginService = new FGLoginServiceImpl();
			loginService.validateAuthToken(userName, token);
			/*
			 * If valid, pass on the filter chain
			 */

			/*
			 * Else, return 401 unauthorized
			 */

		} else {
			/*
			 * return 401 unauthorized
			 */
		}
	}
}
