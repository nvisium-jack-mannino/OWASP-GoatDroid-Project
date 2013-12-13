package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.History;
import org.owasp.goatdroid.webservice.fourgoats.model.HistoryCheckin;

public interface HistoryService {

	public History getHistory(String authToken);

	public History getUserHistory(String userName);
}
