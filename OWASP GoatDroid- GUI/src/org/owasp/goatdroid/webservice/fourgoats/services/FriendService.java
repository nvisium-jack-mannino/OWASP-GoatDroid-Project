package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.FriendBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendListBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.FriendProfileBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PendingFriendRequestsBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.PublicUsersBean;

public interface FriendService {

	public FriendListBean getFriends(String sessionToken);

	public FriendBean requestFriend(String sessionToken,
				String friendUserName);

	public FriendBean acceptOrDenyFriendRequest(String action,
				String sessionToken, String userName);

	public FriendBean removeFriend(String sessionToken,
				String friendUserName);

	public FriendProfileBean getProfile(String sessionToken,
				String friendUserName);

	public PendingFriendRequestsBean getPendingFriendRequests(
				String sessionToken);

	public PublicUsersBean getPublicUsers(String sessionToken);
}
