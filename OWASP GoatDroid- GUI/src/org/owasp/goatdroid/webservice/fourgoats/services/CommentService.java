package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.CommentModel;
import org.owasp.goatdroid.webservice.fourgoats.model.CommentListModel;

public interface CommentService {

	public CommentModel addComment(String sessionToken, String comment,
			String checkinID);

	public CommentModel removeComment(String sessionToken, String commentID);

	public CommentListModel getComments(String sessionToken, String checkinID);
}
