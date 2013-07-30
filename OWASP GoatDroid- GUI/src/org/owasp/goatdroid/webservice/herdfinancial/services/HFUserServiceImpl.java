package org.owasp.goatdroid.webservice.herdfinancial.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.dao.HFUserDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.model.LoginModel;
import org.springframework.stereotype.Service;

@Service
public class HFUserServiceImpl implements UserService {

	@Resource
	HFUserDaoImpl dao;

	public LoginModel signOut(int sessionToken) {

		LoginModel bean = new LoginModel();
		ArrayList<String> errors = new ArrayList<String>();
		try {
			dao.terminateSession(sessionToken);
			bean.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
		}
		return bean;
	}
}
