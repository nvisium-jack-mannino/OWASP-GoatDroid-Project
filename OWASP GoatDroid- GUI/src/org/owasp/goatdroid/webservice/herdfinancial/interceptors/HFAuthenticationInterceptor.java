package org.owasp.goatdroid.webservice.herdfinancial.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.goatdroid.webservice.fourgoats.services.FGLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component()
public class HFAuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	FGLoginServiceImpl loginService;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object arg2,
			ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object arg2)
			throws Exception {
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
			if (!userName.equals("") && !token.equals("")) {
				/*
				 * Now, we check to ensure the authentication token is valid for
				 * the user account
				 */
				boolean isAuthValid = loginService.validateAuthToken(userName,
						token);

				/*
				 * If valid, pass on the filter chain
				 */
			} else {
				// Return 401 unauthorized, bad auth string
			}

		} else {
			/*
			 * return 401 unauthorized
			 */
		}
		httpServletResponse.getWriter().write("things");
		return false;
	}
}
