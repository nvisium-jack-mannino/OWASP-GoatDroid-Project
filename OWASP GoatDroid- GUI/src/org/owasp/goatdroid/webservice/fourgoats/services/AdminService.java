package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.bean.AdminBean;
import org.owasp.goatdroid.webservice.fourgoats.bean.GetUsersAdminBean;

public interface AdminService {

	public AdminBean deleteUser(String sessionToken, String userName);

	public AdminBean resetPassword(String sessionToken, String userName,
			String newPassword);

	public GetUsersAdminBean getUsers(String sessionToken);
}
