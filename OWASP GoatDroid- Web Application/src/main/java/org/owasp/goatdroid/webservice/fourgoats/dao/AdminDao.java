package org.owasp.goatdroid.webservice.fourgoats.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.owasp.goatdroid.webservice.fourgoats.model.User;

public interface AdminDao {

	public boolean isAdmin(String authToken) throws Exception;

	public void deleteUser(String userName) throws Exception;

	public void updatePassword(String userName, String newPassword)
			throws Exception;

	public ArrayList<User> getUsers() throws Exception;

	public void terminateAuth(String AuthToken) throws SQLException;

}
