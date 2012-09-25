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
package org.owasp.goatdroid.webservice.fourgoats.impl;

import java.util.ArrayList;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendProfileBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PendingFriendRequestsBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PublicUsersBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.FriendDAO;

public class Friend {

	static public FriendListBean getFriends(String sessionToken) {

		FriendListBean bean = new FriendListBean();
		ArrayList<String> errors = new ArrayList<String>();
		FriendDAO dao = new FriendDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				String userName = dao.getUserName(userID);

				bean.setFriends(dao.getFriends(userID, userName));
				bean.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public FriendBean requestFriend(String sessionToken,
			String friendUserName) {

		FriendBean bean = new FriendBean();
		ArrayList<String> errors = new ArrayList<String>();
		FriendDAO dao = new FriendDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateUserNameFormat(friendUserName))
				errors.add(Constants.USERNAME_FORMAT_INVALID);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				String friendUserID = dao.getUserIDByName(friendUserName);

				if (!dao.isFriend(userID, friendUserID)) {
					if (!dao.wasFriendRequestSent(friendUserID, userID)) {
						if (!userID.equals(friendUserID)) {
							dao.requestFriend(userID, friendUserID);
							bean.setSuccess(true);
						} else {
							errors.add(Constants.CANNOT_DO_TO_YOURSELF);
						}
					} else {
						errors.add(Constants.FRIEND_ALREADY_REQUESTED);
					}
				} else {
					errors.add(Constants.ALREADY_FRIENDS);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public FriendBean acceptOrDenyFriendRequest(String action,
			String sessionToken, String userName) {

		FriendBean bean = new FriendBean();
		ArrayList<String> errors = new ArrayList<String>();
		FriendDAO dao = new FriendDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateUserNameFormat(userName))
				errors.add(Constants.USERNAME_FORMAT_INVALID);
			else if (!Validators.validateFriendRequestAction(action))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				String friendRequestID = dao.getFriendRequestID(userName,
						userID);

				if (dao.isUserFriendRequested(userID, friendRequestID)) {
					String fromUserID = dao.getFromFriendID(friendRequestID);
					/*
					 * If they accept the friend request we create the friend
					 * record and remove the request from the DB
					 */
					if (action.equals("accept")) {
						dao.addFriend(userID, fromUserID);
						dao.removePendingFriendRequest(friendRequestID);
					} else {
						dao.removePendingFriendRequest(friendRequestID);
					}
					bean.setSuccess(true);
				} else {
					errors.add(Constants.NOT_AUTHORIZED);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public FriendBean removeFriend(String sessionToken,
			String friendUserName) {

		FriendBean bean = new FriendBean();
		ArrayList<String> errors = new ArrayList<String>();
		FriendDAO dao = new FriendDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateUserNameFormat(friendUserName))
				errors.add(Constants.USERNAME_FORMAT_INVALID);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				String friendUserID = dao.getUserIDByName(friendUserName);

				if (!userID.equals(friendUserID)) {
					if (dao.isFriend(userID, friendUserID)) {
						dao.removeFriend(userID, friendUserID);
						bean.setSuccess(true);
					} else {
						errors.add(Constants.NOT_AUTHORIZED);
					}
				} else {
					errors.add(Constants.CANNOT_DO_TO_YOURSELF);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public FriendProfileBean getProfile(String sessionToken,
			String friendUserName) {

		FriendProfileBean bean = new FriendProfileBean();
		ArrayList<String> errors = new ArrayList<String>();
		FriendDAO dao = new FriendDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateUserNameFormat(friendUserName))
				errors.add(Constants.USERNAME_FORMAT_INVALID);

			if (errors.size() == 0) {
				String friendUserID = dao.getUserIDByName(friendUserName);
				// Hmmmm interesting
				/*
				 * if (dao.isFriend(userID, friendUserID) ||
				 * dao.isProfilePublic(friendUserID) ||
				 * userID.equals(friendUserID)) {
				 */
				bean.setProfile(dao.getProfile(friendUserID));
				bean.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public PendingFriendRequestsBean getPendingFriendRequests(
			String sessionToken) {

		PendingFriendRequestsBean bean = new PendingFriendRequestsBean();
		ArrayList<String> errors = new ArrayList<String>();
		FriendDAO dao = new FriendDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				dao.openConnection();
				String userID = dao.getUserID(sessionToken);
				bean.setPendingFriendRequests(dao
						.getPendingFriendRequests(userID));
				bean.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	static public PublicUsersBean getPublicUsers(String sessionToken) {

		PublicUsersBean bean = new PublicUsersBean();
		ArrayList<String> errors = new ArrayList<String>();
		FriendDAO dao = new FriendDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);

			if (errors.size() == 0) {
				String userName = dao.getUserNameBySessionToken(sessionToken);
				bean.setUsers(dao.getPublicUsers(userName));
				bean.setSuccess(true);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			bean.setErrors(errors);
			try {
				dao.closeConnection();
			} catch (Exception e) {
			}
		}
		return bean;
	}
}
