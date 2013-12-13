package org.owasp.goatdroid.webservice.herdfinancial.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.dao.HFUserDaoImpl;
import org.owasp.goatdroid.webservice.herdfinancial.model.BaseModel;
import org.springframework.stereotype.Service;

@Service
public class HFUserServiceImpl implements UserService {

	@Resource
	HFUserDaoImpl dao;

	public BaseModel signOut(String authToken) {

		BaseModel base = new BaseModel();
		ArrayList<String> errors = new ArrayList<String>();
		try {
			dao.terminateAuth(authToken);
			base.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			base.setErrors(errors);
		}
		return base;
	}
}
