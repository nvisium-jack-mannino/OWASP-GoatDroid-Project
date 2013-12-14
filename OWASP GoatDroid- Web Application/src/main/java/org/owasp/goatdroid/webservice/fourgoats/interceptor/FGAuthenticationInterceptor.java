package org.owasp.goatdroid.webservice.fourgoats.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.exception.Base64Exception;
import org.owasp.goatdroid.webservice.fourgoats.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.fourgoats.services.FGLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component()
public class FGAuthenticationInterceptor implements HandlerInterceptor {

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
			HttpServletResponse httpServletResponse, Object arg2) {

		try {
			AuthorizationHeader authHeader = LoginUtils
					.decodeAuthorizationHeader(httpServletRequest
							.getHeader("Authorization"));
			boolean isAuthValid = loginService.validateAuthToken(
					authHeader.getUsername(), authHeader.getAuthToken());
			if (isAuthValid) {
				httpServletRequest.setAttribute("authHeader", authHeader);
				return true;
			} else {
				httpServletResponse.setStatus(401);
				return false;
			}
		} catch (Base64Exception e) {
			httpServletResponse.setStatus(401);
			return false;
		}
	}
}
