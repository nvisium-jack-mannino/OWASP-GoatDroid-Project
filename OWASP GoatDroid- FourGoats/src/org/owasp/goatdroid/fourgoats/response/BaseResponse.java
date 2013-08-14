package org.owasp.goatdroid.fourgoats.response;

import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;
import org.owasp.goatdroid.fourgoats.responseobjects.ResponseObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseResponse {

	static public GenericResponseObject parseJsonResponse(String json,
			Class clazz) {
		Gson gson = new GsonBuilder().create();
		GenericResponseObject responseObject = gson.fromJson(json, clazz);
		return responseObject;
	}

	static public GenericResponseObject getSuccessAndErrors(String response) {
		return parseJsonResponse(response, GenericResponseObject.class);
	}
}
