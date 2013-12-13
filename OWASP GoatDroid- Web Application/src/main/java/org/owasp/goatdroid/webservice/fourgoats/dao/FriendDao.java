package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.owasp.goatdroid.webservice.fourgoats.model.FriendRequest;
import org.owasp.goatdroid.webservice.fourgoats.model.User;

public interface FriendDao {

	public ArrayList<User> getFriends(String userID, String userName)
			throws SQLException;

	public void addFriend(String userID, String friendUserID)
			throws SQLException;

	public boolean isFriend(String userID, String friendUserID)
			throws SQLException;

	public void removeFriend(String userID, String friendUserID)
			throws SQLException;

	public HashMap<String, String> getProfile(String userID)
			throws SQLException;

	public ArrayList<FriendRequest> getPendingFriendRequests(String userID)
			throws SQLException;

	public ArrayList<User> getPublicUsers(String userName)
			throws SQLException;

	public void requestFriend(String userID, String friendUserID)
			throws SQLException;

	public boolean isUserFriendRequested(String toUserID, String requestID)
			throws SQLException;

	public String getFromFriendID(String requestID) throws SQLException;

	public void removePendingFriendRequest(String requestID)
			throws SQLException;

	public boolean wasFriendRequestSent(String fromUserID, String toUserID)
			throws SQLException;

	public String getUserID(String authToken) throws SQLException;

	public String getUserIDByName(String userName) throws SQLException;

	public String getFriendRequestID(String userName, String userID)
			throws SQLException;

	public String getUserName(String userID) throws SQLException;

}
