package org.owasp.goatdroid.webservice.fourgoats.services;

import org.owasp.goatdroid.webservice.fourgoats.model.AdminModel;
import org.owasp.goatdroid.webservice.fourgoats.model.GetUsersAdminModel;
import org.owasp.goatdroid.webservice.fourgoats.model.LoginModel;

public interface AdminService {

	public AdminModel deleteUser(String sessionToken, String userName);

	public AdminModel resetPassword(String sessionToken, String userName,
			String newPassword);

	public GetUsersAdminModel getUsers(String sessionToken);

	public LoginModel signOut(String sessionToken);
}
