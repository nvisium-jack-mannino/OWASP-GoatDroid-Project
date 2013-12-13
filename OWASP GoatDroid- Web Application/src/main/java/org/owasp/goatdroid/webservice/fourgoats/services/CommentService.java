package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.Comment;
import org.owasp.goatdroid.webservice.fourgoats.model.CommentList;

public interface CommentService {

	public Comment addComment(String authToken, String comment,
			String checkinID);

	public Comment removeComment(String authToken, String commentID);

	public CommentList getComments(String authToken, String checkinID);
}
