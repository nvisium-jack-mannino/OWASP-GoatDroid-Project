package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.HashMap;

public interface CommentDao {

	public void insertComment(String dateTime, String commentID, String userID,
			String comment, String checkinID) throws SQLException;

	public void deleteComment(String commentID) throws SQLException;

	public HashMap<String, String> selectComments(String checkinID)
			throws SQLException;

	public boolean isCommentOwner(String userID, String commentID)
			throws SQLException;

	public String getCheckinOwner(String checkinID) throws SQLException;

	public String getCheckinID(String commentID) throws SQLException;

	public HashMap<String, String> getVenueInfo(String checkinID)
			throws SQLException;
}
