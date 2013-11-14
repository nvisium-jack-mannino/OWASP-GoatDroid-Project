package org.owasp.goatdroid.herdfinancial.responseobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Statement extends GenericResponseObject {

	ArrayList<HashMap<String, String>> statementData;

	public ArrayList<HashMap<String, String>> getStatementData() {
		return statementData;
	}

	public void setStatementData(
			ArrayList<HashMap<String, String>> statementData) {
		this.statementData = statementData;
	}
}
