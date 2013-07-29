package org.owasp.goatdroid.webservice.herdfinancial.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.Utils;
import org.owasp.goatdroid.webservice.herdfinancial.Validators;
import org.owasp.goatdroid.webservice.herdfinancial.bean.LoginBean;
import org.owasp.goatdroid.webservice.herdfinancial.dao.HFLoginDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.dao.HFUserDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HFUserServiceImpl implements UserService {

	@Resource
	HFUserDaoImpl dao;

	public LoginBean signOut(int sessionToken) {

		LoginBean bean = new LoginBean();
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
