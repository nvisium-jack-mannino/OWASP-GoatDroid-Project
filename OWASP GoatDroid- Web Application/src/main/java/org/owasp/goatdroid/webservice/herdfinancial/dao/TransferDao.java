package org.owasp.goatdroid.webservice.herdfinancial.dao;

import java.sql.Date;
import java.sql.SQLException;

public interface TransferDao {

	public double getBalance(String accountNumber) throws SQLException;

	public void insertTransaction(String accountNumber, Date date,
			double amount, String name, double balance) throws SQLException;

	public void updateAccountBalance(String accountNumber, double amount,
			double balance) throws SQLException;

	public String getName(String accountNumber) throws SQLException;

	public boolean doesAccountExist(String accountNumber) throws SQLException;
}
