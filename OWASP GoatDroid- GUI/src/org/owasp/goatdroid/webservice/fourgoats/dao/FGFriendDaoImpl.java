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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.model.UserModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FGFriendDaoImpl extends BaseDaoImpl implements FriendDao {

	@Autowired
	public FGFriendDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ArrayList<UserModel> getFriends(String userID, String userName)
			throws SQLException {

		String sql = "SELECT app.fg_friends.userID, app.fg_users.userName, app.fg_friends.friendUserID, app.fg_users.firstName, "
				+ "app.fg_users.lastName FROM app.fg_friends INNER JOIN app.fg_users ON app.fg_users.userID = app.fg_friends.userID OR "
				+ "app.fg_users.userID = app.fg_friends.friendUserID WHERE app.fg_friends.friendUserId = ? OR app.fg_friends.userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, userID });
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

		String sql = "INSERT INTO app.fg_friends (userID, friendUserID) VALUES (?,?)";
		getJdbcTemplate().update(sql, new Object[] { userID, friendUserID });
	}

	public boolean isFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "SELECT userID FROM app.fg_friends WHERE (userID = ? and friendUserID = ?) OR (friendUserID = ? and userID = ?) ";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userID, friendUserID, userID, friendUserID });
		if (rs.next())
			return true;
		else
			return false;
	}

	public void removeFriend(String userID, String friendUserID)
			throws SQLException {

		String sql = "DELETE FROM app.fg_friends WHERE (userID = ? AND friendUserID = ?) OR (friendUserID = ? AND userID = ?)";
		getJdbcTemplate().update(sql,
				new Object[] { userID, friendUserID, friendUserID, userID });
	}

	public HashMap<String, String> getProfile(String userID)
			throws SQLException {

		String sql = "SELECT userID, firstName, lastName, lastCheckinTime, lastLatitude, lastLongitude FROM app.fg_users WHERE userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
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

		String sql = "SELECT app.fg_friendrequests.requestID, app.fg_users.userName, "
				+ "app.fg_users.firstName, app.fg_users.lastName FROM "
				+ "app.fg_friendrequests INNER JOIN app.fg_users ON app.fg_users.userID = app.fg_friendrequests.fromUserID "
				+ "WHERE app.fg_friendrequests.toUserID = ? ";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
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

		String sql = "SELECT userID, userName, firstName, lastName fFROM app.fg_users WHERE isPublic = true";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql);
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

		String sql = "INSERT INTO app.fg_friendrequests (requestID, fromUserID, toUserID) VALUES (?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] {
						LoginUtils.generateSaltedSHA256Hash(
								userID + friendUserID
										+ LoginUtils.getTimeMilliseconds(),
								Salts.FRIEND_REQUEST_ID_GENERATOR_SALT),
						userID, friendUserID });
	}

	public boolean isUserFriendRequested(String toUserID, String requestID)
			throws SQLException {

		String sql = "SELECT toUserID FROM app.fg_friendrequests WHERE requestID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, requestID);
		rs.next();
		if (rs.getString("toUserID").equals(toUserID))
			return true;
		else
			return false;
	}

	public String getFromFriendID(String requestID) throws SQLException {

		String sql = "SELECT fromUserID FROM app.fg_friendrequests WHERE requestID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, requestID);
		rs.next();
		return rs.getString("fromUserID");
	}

	public void removePendingFriendRequest(String requestID)
			throws SQLException {

		String sql = "DELETE FROM friendrequests WHERE app.fg_requestID = ?";
		getJdbcTemplate().update(sql, requestID);
	}

	public boolean wasFriendRequestSent(String fromUserID, String toUserID)
			throws SQLException {

		String sql = "SELECT requestID FROM app.fg_friendrequests WHERE (fromUserID = ? AND toUserID = ?) OR (fromUserID = ? AND toUserID = ?)";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { fromUserID, toUserID, toUserID, fromUserID });
		if (rs.next())
			return true;
		else
			return false;
	}

	public String getUserID(String sessionToken) throws SQLException {

		String sql = "SELECT userID FROM app.fg_users WHERE sessionToken = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, sessionToken);
		rs.next();
		return rs.getString("userID");
	}

	public String getUserIDByName(String userName) throws SQLException {

		String sql = "SELECT userID FROM app.fg_users WHERE userName = ?";
		;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userName);
		rs.next();
		return rs.getString("userID");

	}

	public String getFriendRequestID(String userName, String userID)
			throws SQLException {

		String sql = "SELECT requestID FROM app.fg_friendrequests INNER JOIN app.fg_users "
				+ "on app.fg_users.userID = app.fg_friendRequests.fromUserID WHERE app.fg_users.userName = ? and app.fg_friendRequests.toUserID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { userName, userID });
		rs.next();
		return rs.getString("requestID");

	}

	public String getUserName(String userID) throws SQLException {

		String sql = "SELECT userName FROM app.fg_users WHERE userID = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, userID);
		rs.next();
		return rs.getString("userName");
	}
}
