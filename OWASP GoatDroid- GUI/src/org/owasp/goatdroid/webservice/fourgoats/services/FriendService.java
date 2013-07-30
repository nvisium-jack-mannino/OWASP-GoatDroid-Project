package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.FriendModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendListModel;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendProfileModel;
import org.owasp.goatdroid.webservice.fourgoats.model.PendingFriendRequestsModel;
import org.owasp.goatdroid.webservice.fourgoats.model.PublicUsersModel;

public interface FriendService {

	public FriendListModel getFriends(String sessionToken);

	public FriendModel requestFriend(String sessionToken,
				String friendUserName);

	public FriendModel acceptOrDenyFriendRequest(String action,
				String sessionToken, String userName);

	public FriendModel removeFriend(String sessionToken,
				String friendUserName);

	public FriendProfileModel getProfile(String sessionToken,
				String friendUserName);

	public PendingFriendRequestsModel getPendingFriendRequests(
				String sessionToken);

	public PublicUsersModel getPublicUsers(String sessionToken);
}
