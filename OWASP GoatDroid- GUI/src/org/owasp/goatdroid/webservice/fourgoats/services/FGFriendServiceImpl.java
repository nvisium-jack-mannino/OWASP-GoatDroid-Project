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
package org.owasp.goatdroid.webservice.fourgoats.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.dao.FGFriendDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendListModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendProfileModel;
import org.owasp.goatdroid.webservice.fourgoats.model.PendingFriendRequestsModel;
import org.owasp.goatdroid.webservice.fourgoats.model.PublicUsersModel;
import org.springframework.stereotype.Service;

@Service
public class FGFriendServiceImpl implements FriendService {

	@Resource
	FGFriendDaoImpl dao;

	public FriendListModel getFriends(String authToken) {

		FriendListModel friendList = new FriendListModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			String userID = dao.getUserID(authToken);
			String userName = dao.getUserName(userID);
			friendList.setFriends(dao.getFriends(userID, userName));
			friendList.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			friendList.setErrors(errors);
		}
		return friendList;
	}

	public FriendModel requestFriend(String authToken, String friendUserName) {

		FriendModel friend = new FriendModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			String userID = dao.getUserID(authToken);
			String friendUserID = dao.getUserIDByName(friendUserName);

			if (!dao.isFriend(userID, friendUserID)) {
				if (!dao.wasFriendRequestSent(friendUserID, userID)) {
					if (!userID.equals(friendUserID)) {
						dao.requestFriend(userID, friendUserID);
						friend.setSuccess(true);
					} else {
						errors.add(Constants.CANNOT_DO_TO_YOURSELF);
					}
				} else {
					errors.add(Constants.FRIEND_ALREADY_REQUESTED);
				}
			} else {
				errors.add(Constants.ALREADY_FRIENDS);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			friend.setErrors(errors);
		}
		return friend;
	}

	public FriendModel acceptOrDenyFriendRequest(String action,
			String authToken, String userName) {

		FriendModel friend = new FriendModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			if (!Validators.validateFriendRequestAction(action))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(authToken);
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
					friend.setSuccess(true);
				} else {
					errors.add(Constants.NOT_AUTHORIZED);
				}
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			friend.setErrors(errors);
		}
		return friend;
	}

	public FriendModel removeFriend(String authToken, String friendUserName) {

		FriendModel friend = new FriendModel();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			String userID = dao.getUserID(authToken);
			String friendUserID = dao.getUserIDByName(friendUserName);

			if (!userID.equals(friendUserID)) {
				if (dao.isFriend(userID, friendUserID)) {
					dao.removeFriend(userID, friendUserID);
					friend.setSuccess(true);
				} else {
					errors.add(Constants.NOT_AUTHORIZED);
				}
			} else {
				errors.add(Constants.CANNOT_DO_TO_YOURSELF);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			friend.setErrors(errors);
		}
		return friend;
	}

	public FriendProfileModel getProfile(String authToken,
			String friendUserName) {

		FriendProfileModel friendProfile = new FriendProfileModel();
		ArrayList<String> errors = new ArrayList<String>();
		try {
			String friendUserID = dao.getUserIDByName(friendUserName);
			// Hmmmm interesting
			/*
			 * if (dao.isFriend(userID, friendUserID) ||
			 * dao.isProfilePublic(friendUserID) || userID.equals(friendUserID))
			 * {
			 */
			friendProfile.setProfile(dao.getProfile(friendUserID));
			friendProfile.setSuccess(true);
			// }
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			friendProfile.setErrors(errors);
		}
		return friendProfile;
	}

	public PendingFriendRequestsModel getPendingFriendRequests(
			String authToken) {

		PendingFriendRequestsModel pendingRequests = new PendingFriendRequestsModel();
		ArrayList<String> errors = new ArrayList<String>();
		try {
			String userID = dao.getUserID(authToken);
			pendingRequests.setPendingFriendRequests(dao.getPendingFriendRequests(userID));
			pendingRequests.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			pendingRequests.setErrors(errors);
		}
		return pendingRequests;
	}

	public PublicUsersModel getPublicUsers(String authToken) {

		PublicUsersModel publicUsers = new PublicUsersModel();
		ArrayList<String> errors = new ArrayList<String>();
		try {
			String userName = dao.getUserNameByAuthToken(authToken);
			publicUsers.setUsers(dao.getPublicUsers(userName));
			publicUsers.setSuccess(true);
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			publicUsers.setErrors(errors);
		}
		return publicUsers;
	}
}
