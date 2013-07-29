package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HFUserDaoImpl extends BaseDaoImpl implements UserDao {

	@Autowired
	public HFUserDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void terminateSession(int sessionToken) throws SQLException {

		String sql = "UPDATE app.hf_users SET sessionToken = 0, sessionStartTime = 0 WHERE sessionToken = ?";
		getJdbcTemplate().update(sql, sessionToken);
	}
}
