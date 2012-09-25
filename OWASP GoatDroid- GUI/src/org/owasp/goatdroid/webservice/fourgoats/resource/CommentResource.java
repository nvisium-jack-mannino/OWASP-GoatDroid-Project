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
package org.owasp.goatdroid.webservice.fourgoats.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.CookieParam;
import org.owasp.goatdroid.webservice.fourgoats.Constants;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.CommentBean;
import org.owasp.goatdroid.webservice.fourgoats.impl.Comment;

@Path("/fourgoats/api/v1/comments")
public class CommentResource {

	@Path("add")
	@POST
	@Produces("application/json")
	public CommentBean addComment(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("comment") String comment,
			@FormParam("checkinID") String checkinID) {
		try {
			return Comment.addComment(sessionToken, comment, checkinID);
		} catch (NullPointerException e) {
			CommentBean bean = new CommentBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("remove")
	@POST
	@Produces("application/json")
	public CommentBean removeComment(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@FormParam("commentID") String commentID) {
		try {
			return Comment.removeComment(sessionToken, commentID);
		} catch (NullPointerException e) {
			CommentBean bean = new CommentBean();
			bean.setSuccess(false);
			return bean;
		}
	}

	@Path("get/{checkinID}")
	@GET
	@Produces("application/json")
	public CommentListBean getComments(
			@CookieParam(Constants.SESSION_TOKEN_NAME) String sessionToken,
			@PathParam("checkinID") String checkinID) {
		try {
			return Comment.getComments(sessionToken, checkinID);
		} catch (NullPointerException e) {
			CommentListBean bean = new CommentListBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
