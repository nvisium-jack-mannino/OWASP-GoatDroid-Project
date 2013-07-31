package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.FriendModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendListModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendProfileModel;
import org.owasp.goatdroid.webservice.fourgoats.model.PendingFriendRequestsModel;
import org.owasp.goatdroid.webservice.fourgoats.model.PublicUsersModel;

public interface FriendService {

	public FriendListModel getFriends(String authToken);

	public FriendModel requestFriend(String authToken,
				String friendUserName);

	public FriendModel acceptOrDenyFriendRequest(String action,
				String authToken, String userName);

	public FriendModel removeFriend(String authToken,
				String friendUserName);

	public FriendProfileModel getProfile(String authToken,
				String friendUserName);

	public PendingFriendRequestsModel getPendingFriendRequests(
				String authToken);

	public PublicUsersModel getPublicUsers(String authToken);
}
