package org.owasp.goatdroid.webservice.herdfinancial.controllers;

import javax.servlet.http.HttpServletRequest;

import org.owasp.goatdroid.webservice.herdfinancial.model.AuthorizationHeader;
import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/priv/user", produces = "application/json")
public class HFUserController {

	@Autowired
	HFUserServiceImpl userService;

	@RequestMapping(value = "sign-out", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel signOut(HttpServletRequest request) {
		try {
			AuthorizationHeader authHeader = (AuthorizationHeader) request
					.getAttribute("authHeader");
			return userService.signOut(authHeader.getAuthToken());
		} catch (NullPointerException e) {
			BaseModel base = new BaseModel();
			base.setSuccess(false);
			return base;
		}
	}
}
