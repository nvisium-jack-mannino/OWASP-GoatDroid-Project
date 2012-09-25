/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.gui.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.owasp.goatdroid.gui.Utils;

public class QueryUtils {

	static public ArrayList<String> getTables(File database)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		String DB_CONNECTION_STRING = "jdbc:derby:" + database;

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		Connection conn = (Connection) DriverManager
				.getConnection(DB_CONNECTION_STRING);
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getTables(null, null, null, null);
		ArrayList<String> tables = new ArrayList<String>();
		while (rs.next()) {
			/*
			 * We only want application tables Don't really care about the rest
			 * of the schema
			 */
			if (rs.getString("TABLE_SCHEM").equals("APP"))
				tables.add(rs.getString("TABLE_NAME"));
		}
		conn.close();
		return tables;
	}

	static public DefaultTableModel executeSelectStatement(String database,
			String query) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		String DB_CONNECTION_STRING;
		if (Utils.getOS().startsWith("windows"))
			DB_CONNECTION_STRING = "jdbc:derby:"
					+ Utils.getCurrentPath().replaceFirst("^/", "") + "dbs/"
					+ database;
		else
			DB_CONNECTION_STRING = "jdbc:derby:" + Utils.getCurrentPath()
					+ "dbs/" + database;
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		Connection conn = (Connection) DriverManager
				.getConnection(DB_CONNECTION_STRING);
		PreparedStatement sqlStatement = (PreparedStatement) conn
				.prepareCall(query);
		ResultSet rs = sqlStatement.executeQuery();
		return formatTableData(rs);
	}

	static public void executeUpdateStatement(String database, String query)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		String DB_CONNECTION_STRING;
		if (Utils.getOS().startsWith("windows"))
			DB_CONNECTION_STRING = "jdbc:derby:"
					+ Utils.getCurrentPath().replaceFirst("^/", "") + "dbs/"
					+ database;
		else
			DB_CONNECTION_STRING = "jdbc:derby:" + Utils.getCurrentPath()
					+ "dbs/" + database;

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		Connection conn = (Connection) DriverManager
				.getConnection(DB_CONNECTION_STRING);
		PreparedStatement sqlStatement = (PreparedStatement) conn
				.prepareCall(query);
		sqlStatement.executeUpdate();
	}

	static public DefaultTableModel formatTableData(ResultSet rs)
			throws SQLException {

		ResultSetMetaData rsMetaData = rs.getMetaData();
		int columnCount = rsMetaData.getColumnCount();
		String columnNames[] = new String[columnCount];
		String columnTypes[] = new String[columnCount];
		for (int count = 1; count <= columnCount; count++) {
			columnNames[count - 1] = rsMetaData.getColumnName(count);
			/*
			 * We get the column type so we can correctly cast the column to a
			 * string.
			 */
			columnTypes[count - 1] = rsMetaData.getColumnTypeName(count);
		}
		ArrayList<String[]> rowData = new ArrayList<String[]>();
		while (rs.next()) {
			String[] row = new String[columnCount];
			int count = 0;
			int columnNameCount = 0;
			for (String column : columnNames) {
				if (columnTypes[columnNameCount].equals("VARCHAR"))
					row[count] = rs.getString(column);
				else if (columnTypes[columnNameCount].equals("INTEGER"))
					row[count] = Integer.toString(rs.getInt(column));
				else if (columnTypes[columnNameCount].equals("BOOLEAN"))
					row[count] = Boolean.toString(rs.getBoolean(column));
				else if (columnTypes[columnNameCount].equals("BIGINT"))
					row[count] = Double.toString(rs.getDouble(column));
				else
					row[count] = rs.getString(column);
				count++;
				columnNameCount++;
			}
			rowData.add(row);
			columnNameCount = 0;
		}
		String tableData[][] = new String[rowData.size()][columnCount];
		int rowCount = 0;
		for (String[] row : rowData) {
			tableData[rowCount] = row;
			rowCount++;
		}
		DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
		return model;
	}
}
