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
import java.util.HashMap;

import javax.annotation.Resource;

import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.LoginUtils;
import org.owasp.goatdroid.webservice.fourgoats.Salts;
import org.owasp.goatdroid.webservice.fourgoats.Validators;
import org.owasp.goatdroid.webservice.fourgoats.dao.FGCommentDaoImpl;
import org.owasp.goatdroid.webservice.fourgoats.model.CommentList;
import org.owasp.goatdroid.webservice.fourgoats.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class FGCommentServiceImpl implements CommentService {

	@Resource
	FGCommentDaoImpl dao;

	public Comment addComment(String authToken, String comment,
			String checkinID) {

		Comment commentModel = new Comment();
		ArrayList<String> errors = new ArrayList<String>();

		try {

			if (!Validators.validateCommentFields(comment, checkinID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {

				String userID = dao.getUserID(authToken);
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
					commentModel.setSuccess(true);
					return commentModel;
				} else
					errors.add(Constants.NOT_AUTHORIZED);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			commentModel.setErrors(errors);
		}
		return commentModel;
	}

	public Comment removeComment(String authToken, String commentID) {

		Comment commentModel = new Comment();
		ArrayList<String> errors = new ArrayList<String>();

		try {

			if (!Validators.validateIDFormat(commentID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(authToken);
				String checkinID = dao.getCheckinID(commentID);
				String checkinOwner = dao.getCheckinOwner(checkinID);
				if (checkinOwner.equals(userID)
						|| dao.isCommentOwner(userID, commentID)) {
					dao.deleteComment(commentID);
					commentModel.setSuccess(true);
				} else
					errors.add(Constants.NOT_AUTHORIZED);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			commentModel.setErrors(errors);
		}
		return commentModel;
	}

	public CommentList getComments(String authToken, String checkinID) {

		CommentList commentList = new CommentList();
		ArrayList<String> errors = new ArrayList<String>();

		try {
			if (!Validators.validateIDFormat(checkinID))
				errors.add(Constants.UNEXPECTED_ERROR);

			if (errors.size() == 0) {
				String userID = dao.getUserID(authToken);
				String checkinOwner = dao.getCheckinOwner(checkinID);
				if (checkinOwner.equals(userID)
						|| dao.isFriend(userID, checkinOwner)
						|| dao.isCheckinOwnerProfilePublic(checkinID)) {

					commentList.setComments(dao.selectComments(checkinID));
					HashMap<String, String> venueData = dao
							.getVenueInfo(checkinID);
					commentList.setVenueName(venueData.get("venueName"));
					commentList.setVenueWebsite(venueData.get("venueWebsite"));
					commentList.setSuccess(true);
				} else
					errors.add(Constants.NOT_AUTHORIZED);
			}
		} catch (Exception e) {
			errors.add(Constants.UNEXPECTED_ERROR);
		} finally {
			commentList.setErrors(errors);
		}
		return commentList;
	}
}
