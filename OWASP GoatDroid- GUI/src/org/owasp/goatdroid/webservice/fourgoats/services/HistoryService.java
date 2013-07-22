package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.HistoryBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.HistoryCheckinBean;

public interface HistoryService {

	public HistoryBean getHistory(String sessionToken);

	public HistoryCheckinBean getCheckin(String sessionToken, String checkinID);

	public HistoryBean getUserHistory(String sessionToken, String userName);
}
