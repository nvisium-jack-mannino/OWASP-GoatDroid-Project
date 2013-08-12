package org.owasp.goatdroid.herdfinancial.responseobjects;

import java.util.ArrayList;

public class BaseResponseObject implements ResponseObject {

	ArrayList<String> errors;
	boolean success;

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
