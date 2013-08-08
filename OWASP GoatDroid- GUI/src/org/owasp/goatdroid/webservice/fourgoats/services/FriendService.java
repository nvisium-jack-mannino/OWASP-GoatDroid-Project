package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.Friend;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendList;
import org.owasp.goatdroid.webservice.fourgoats.model.FriendProfile;
import org.owasp.goatdroid.webservice.fourgoats.model.PendingFriendRequests;
import org.owasp.goatdroid.webservice.fourgoats.model.PublicUsers;

public interface FriendService {

	public FriendList getFriends(String authToken);

	public Friend requestFriend(String authToken,
				String friendUserName);

	public Friend acceptOrDenyFriendRequest(String action,
				String authToken, String userName);

	public Friend removeFriend(String authToken,
				String friendUserName);

	public FriendProfile getProfile(String authToken,
				String friendUserName);

	public PendingFriendRequests getPendingFriendRequests(
				String authToken);

	public PublicUsers getPublicUsers(String authToken);
}
