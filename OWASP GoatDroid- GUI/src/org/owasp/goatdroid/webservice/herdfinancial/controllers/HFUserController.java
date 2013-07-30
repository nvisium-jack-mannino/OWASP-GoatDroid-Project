package org.owasp.goatdroid.webservice.herdfinancial.controllers;

import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.model.LoginModel;
import org.owasp.goatdroid.webservice.herdfinancial.services.HFUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "herdfinancial/api/v1/priv/user", produces = "application/json")
public class HFUserController {

	@Autowired
	HFUserServiceImpl userService;

	@RequestMapping(value = "sign_out", method = RequestMethod.GET)
	@ResponseBody
	public LoginModel signOut(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken) {
		try {
			return userService.signOut(sessionToken);
		} catch (NullPointerException e) {
			LoginModel bean = new LoginModel();
			bean.setSuccess(false);
			return bean;
		}
	}
}
