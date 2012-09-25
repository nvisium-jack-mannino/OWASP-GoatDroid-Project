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
import java.util.HashMap;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentBean;
import org.owasp.goatdroid.webservice.fourgoats.dao.CommentDAO;

public class Comment {

	static public CommentBean addComment(String sessionToken, String comment,
			String checkinID) {

		CommentBean bean = new CommentBean();
		ArrayList<String> errors = new ArrayList<String>();
		CommentDAO dao = new CommentDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateCommentFields(comment, checkinID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {

				String userID = dao.getUserID(sessionToken);
				String checkinOwner = dao.getCheckinOwner(checkinID);
				if (checkinOwner.equals(userID)
						|| dao.isFriend(userID, checkinOwner)) {

					String commentID = LoginUtils.generateSaltedSHA256Hash(
							userID + comment + checkinID,
							Salts.COMMENT_ID_GENERATOR_SALT
									+ LoginUtils.getTimeMilliseconds());
					String dateTime = LoginUtils.getCurrentDateTime();
					dao.insertComment(dateTime, commentID, userID, comment,
							checkinID);
					bean.setSuccess(true);
					return bean;
				} else
					errors.add(Constants.NOT_AUTHORIZED);
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

	static public CommentBean removeComment(String sessionToken,
			String commentID) {

		CommentBean bean = new CommentBean();
		ArrayList<String> errors = new ArrayList<String>();
		CommentDAO dao = new CommentDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateIDFormat(commentID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				String checkinID = dao.getCheckinID(commentID);
				String checkinOwner = dao.getCheckinOwner(checkinID);
				if (checkinOwner.equals(userID)
						|| dao.isCommentOwner(userID, commentID)) {
					dao.deleteComment(commentID);
					bean.setSuccess(true);
				} else
					errors.add(Constants.NOT_AUTHORIZED);
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

	static public CommentListBean getComments(String sessionToken,
			String checkinID) {

		CommentListBean bean = new CommentListBean();
		ArrayList<String> errors = new ArrayList<String>();
		CommentDAO dao = new CommentDAO();

		try {
			dao.openConnection();
			if (!dao.isSessionValid(sessionToken)
					|| !Validators.validateSessionTokenFormat(sessionToken))
				errors.add(Constants.INVALID_SESSION);
			else if (!Validators.validateIDFormat(checkinID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(sessionToken);
				String checkinOwner = dao.getCheckinOwner(checkinID);
				if (checkinOwner.equals(userID)
						|| dao.isFriend(userID, checkinOwner)
						|| dao.isCheckinOwnerProfilePublic(checkinID)) {

					HashMap<String, String> comments = new HashMap<String, String>();
					comments = dao.selectComments(checkinID);
					HashMap<String, String> venueData = dao
							.getVenueInfo(checkinID);
					comments.put("venueName", venueData.get("venueName"));
					comments.put("venueWebsite", venueData.get("venueWebsite"));
					bean.setComments(comments);
					bean.setSuccess(true);
				} else
					errors.add(Constants.NOT_AUTHORIZED);
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
