package org.owasp.goatdroid.fourgoats.response;

import org.owasp.goatdroid.fourgoats.responseobjects.BaseResponseObject;
import org.owasp.goatdroid.fourgoats.responseobjects.ResponseObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseResponse {

	static public ResponseObject parseJsonResponse(String json, Class clazz) {
		Gson gson = new GsonBuilder().create();
		ResponseObject responseObject = gson.fromJson(json, clazz);
		return responseObject;
	}
	
	static public ResponseObject getSuccessAndErrors(String response) {
		return parseJsonResponse(response, BaseResponseObject.class);
	}
}
