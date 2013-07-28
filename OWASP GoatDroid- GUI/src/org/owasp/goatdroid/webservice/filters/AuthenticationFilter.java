package org.owasp.goatdroid.webservice.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.deploy.providers.WebAppProvider.Filter;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) arg0;
		String authorizationString = request.getHeader("authorization");
		authorizationString.length();
	}

}
