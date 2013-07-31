package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.HistoryModel;
import org.owasp.goatdroid.webservice.fourgoats.model.HistoryCheckinModel;

public interface HistoryService {

	public HistoryModel getHistory(String authToken);

	public HistoryModel getUserHistory(String userName);
}
