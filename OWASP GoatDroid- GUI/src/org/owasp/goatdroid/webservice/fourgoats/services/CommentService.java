package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.CommentBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentListBean;

public interface CommentService {

	public CommentBean addComment(String sessionToken, String comment,
			String checkinID);

	public CommentBean removeComment(String sessionToken, String commentID);

	public CommentListBean getComments(String sessionToken, String checkinID);
}
