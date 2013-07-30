package org.owasp.goatdroid.webservice.herdfinancial.controllers;

import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.LoginBean;
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

	HFUserServiceImpl userService;

	@Autowired
	public HFUserController(HFUserServiceImpl userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "sign_out", method = RequestMethod.GET)
	@ResponseBody
	public LoginBean signOut(
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken) {
		try {
			return userService.signOut(sessionToken);
		} catch (NullPointerException e) {
			LoginBean bean = new LoginBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
