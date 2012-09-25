/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.model.UserModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendRequestModel;

public class FriendDAO extends BaseDAO {

	public FriendDAO() {
		super();
	}

	public ArrayList<UserModel> getFriends(String userID, String userName)
			throws SQLException {

		String sql = "select friends.userID, users.userName, friends.friendUserID, users.firstName, "
				+ "users.lastName from friends inner join users on users.userID = friends.userID or "
				+ "users.userID = friends.friendUserID where friends.friendUserId = ? or friends.userID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		selectStatement.setString(2, userID);
		ResultSet rs = selectStatement.executeQuery();
		ArrayList<UserModel> friends = new ArrayList<UserModel>();
		while (rs.next()) {
			/*
			 * we check to make sure that we aren't referring to ourselves as
			 * friends if the retrieved ID doesn't match the target account
			 */
			if (!rs.getString("userName").equals(userName)) {
				UserModel friend = new UserModel();
				friend.setUserID(rs.getString("userID"));
				friend.setUserName(rs.getString("userName"));
				friend.setFirstName(rs.getString("firstName"));
				friend.setLastName(rs.getString("lastName"));
				friends.add(friend);
			}
		}
		return friends;
	}

	public void addFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "insert into friends (userID, friendUserID) values (?,?)";
		PreparedStatement insertStatement = (PreparedStatement) conn
				.prepareCall(sql);
		insertStatement.setString(1, userID);
		insertStatement.setString(2, friendUserID);
		insertStatement.executeUpdate();
	}

	public boolean isFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "select userID from friends where (userID = ? and friendUserID = ?) or (friendUserID = ? and userID = ?) ";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		selectStatement.setString(2, friendUserID);
		selectStatement.setString(3, userID);
		selectStatement.setString(4, friendUserID);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public void removeFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "delete from friends where (userID = ? and friendUserID = ?) or (friendUserID = ? and userID = ?)";
		PreparedStatement deleteStatement = (PreparedStatement) conn
				.prepareCall(sql);
		deleteStatement.setString(1, userID);
		deleteStatement.setString(2, friendUserID);
		deleteStatement.setString(3, friendUserID);
		deleteStatement.setString(4, userID);
		deleteStatement.executeUpdate();
	}

	public HashMap<String, String> getProfile(String userID)
			throws SQLException {

		String sql = "select userID, firstName, lastName, lastCheckinTime, lastLatitude, lastLongitude from users where userID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		ResultSet rs = selectStatement.executeQuery();
		HashMap<String, String> profile = new HashMap<String, String>();
		while (rs.next()) {
			profile.put("userID", userID);
			profile.put("firstName", rs.getString("firstName"));
			profile.put("lastName", rs.getString("lastName"));
			profile.put("lastCheckinTime", rs.getString("lastCheckinTime"));
			profile.put("lastLatitude", rs.getString("lastLatitude"));
			profile.put("lastLongitude", rs.getString("lastLongitude"));
		}
		return profile;

	}

	public ArrayList<FriendRequestModel> getPendingFriendRequests(String userID)
			throws SQLException {

		String sql = "select friendrequests.requestID, users.userName, "
				+ "users.firstName, users.lastName from "
				+ "friendrequests inner join users on users.userID = friendrequests.fromUserID "
				+ "where friendrequests.toUserID = ? ";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		ResultSet rs = selectStatement.executeQuery();
		ArrayList<FriendRequestModel> requests = new ArrayList<FriendRequestModel>();
		while (rs.next()) {
			FriendRequestModel request = new FriendRequestModel();
			request.setRequestID(rs.getString("requestID"));
			request.setUserName(rs.getString("userName"));
			request.setFirstName(rs.getString("firstName"));
			request.setLastName(rs.getString("lastName"));
			requests.add(request);
		}
		return requests;
	}

	public ArrayList<UserModel> getPublicUsers(String userName)
			throws SQLException {

		String sql = "select userID, userName, firstName, lastName from users where isPublic = true";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		ResultSet rs = selectStatement.executeQuery();
		ArrayList<UserModel> users = new ArrayList<UserModel>();
		while (rs.next()) {
			// checks to make sure we aren't returning ourselves
			if (!(rs.getString("userName").equals(userName))) {
				UserModel user = new UserModel();
				user.setUserID(rs.getString("userID"));
				user.setUserName(rs.getString("userName"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				users.add(user);
			}
		}
		return users;
	}

	public void requestFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "insert into friendrequests (requestID, fromUserID, toUserID) values (?,?,?)";
		PreparedStatement insertStatement = (PreparedStatement) conn
				.prepareCall(sql);
		insertStatement.setString(1, LoginUtils.generateSaltedSHA256Hash(userID
				+ friendUserID + LoginUtils.getTimeMilliseconds(),
				Salts.FRIEND_REQUEST_ID_GENERATOR_SALT));
		insertStatement.setString(2, userID);
		insertStatement.setString(3, friendUserID);
		insertStatement.executeUpdate();
	}

	public boolean isUserFriendRequested(String toUserID, String requestID)
			throws SQLException {

		String sql = "select toUserID from friendrequests where requestID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, requestID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		if (rs.getString("toUserID").equals(toUserID))
			return true;
		else
			return false;
	}

	public String getFromFriendID(String requestID) throws SQLException {

		String sql = "select fromUserID from friendrequests where requestID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, requestID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("fromUserID");
	}

	public void removePendingFriendRequest(String requestID)
			throws SQLException {

		String sql = "delete from friendrequests where requestID = ?";
		PreparedStatement deleteStatement = (PreparedStatement) conn
				.prepareCall(sql);
		deleteStatement.setString(1, requestID);
		deleteStatement.executeUpdate();
	}

	public boolean wasFriendRequestSent(String fromUserID, String toUserID)
			throws SQLException {

		String sql = "select requestID from friendrequests where (fromUserID = ? and toUserID = ?) or (fromUserID = ? and toUserID = ?)";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, fromUserID);
		selectStatement.setString(2, toUserID);
		selectStatement.setString(3, toUserID);
		selectStatement.setString(4, fromUserID);
		ResultSet rs = selectStatement.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getUserID(String sessionToken) throws SQLException {

		String sql = "select userID from users where sessionToken = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, sessionToken);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("userID");
	}

	public String getUserIDByName(String userName) throws SQLException {

		String sql = "select userID from users where userName = ?";
		;
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userName);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("userID");

	}

	public String getFriendRequestID(String userName, String userID)
			throws SQLException {

		String sql = "select requestID from friendrequests inner join users "
				+ "on users.userID = friendRequests.fromUserID where users.userName = ? and friendRequests.toUserID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userName);
		selectStatement.setString(2, userID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("requestID");

	}

	public String getUserName(String userID) throws SQLException {

		String sql = "select userName from users where userID = ?";
		PreparedStatement selectStatement = (PreparedStatement) conn
				.prepareCall(sql);
		selectStatement.setString(1, userID);
		ResultSet rs = selectStatement.executeQuery();
		rs.next();
		return rs.getString("userName");
	}
}
