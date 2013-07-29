package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import org.owasp.goatdroid.webservice.fourgoats.model.UserModel;

public interface AdminDao {

	public boolean isAdmin(String sessionToken) throws Exception;

	public void deleteUser(String userName) throws Exception;

	public void updatePassword(String userName, String newPassword)
			throws Exception;

	public ArrayList<UserModel> getUsers() throws Exception;

	public void terminateSession(String sessionToken) throws SQLException;

}
