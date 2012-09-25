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
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.owasp.goatdroid.gui.Constants;
import org.owasp.goatdroid.gui.Utils;

public class DatabaseTreeBuilder {

	static String path;

	public DatabaseTreeBuilder() {

		path = Utils.getCurrentPath() + Constants.DATABASE_DIRECTORY;
	}

	public JTree buildTree() {
		DefaultMutableTreeNode dbNode = createNodes();
		JTree tree = new JTree(dbNode);
		return tree;
	}

	public DefaultMutableTreeNode createNodes() {
		// this is the top node of our tree
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Databases");

		try {

			ArrayList<File> databases = getDatabases();
			for (File db : databases) {
				DefaultMutableTreeNode dbNode;
				String[] splitDBPath = db.toString().split(
						Utils.getFileSeparator());
				dbNode = new DefaultMutableTreeNode(
						splitDBPath[splitDBPath.length - 1]);
				/*
				 * As we add each database, we also enumerate its tables. We
				 * return the tables to the user by adding one at a time to the
				 * database node
				 */
				for (String tableName : getTables(db)) {
					DefaultMutableTreeNode dbChild = new DefaultMutableTreeNode(
							tableName);
					dbNode.add(dbChild);
				}
				topNode.add(dbNode);
			}
		}

		catch (NullPointerException e) {
			topNode = new DefaultMutableTreeNode("Error Populating Databases");
		} catch (ClassNotFoundException e) {
			topNode = new DefaultMutableTreeNode("Error Populating Databases");
		} catch (IllegalAccessException e) {
			topNode = new DefaultMutableTreeNode("Error Populating Databases");
		} catch (InstantiationException e) {
			topNode = new DefaultMutableTreeNode("Error Populating Databases");
		} catch (SQLException e) {
			topNode = new DefaultMutableTreeNode("Error Populating Databases");
		}
		return topNode;
	}

	static public ArrayList<File> getDatabases() throws NullPointerException {
		ArrayList<File> databases = new ArrayList<File>();

		File databaseDirectory = new File(path);

		for (File db : databaseDirectory.listFiles()) {
			if (db.isDirectory())
				databases.add(db);
		}
		return databases;
	}

	static public ArrayList<String> getTables(File database)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		return QueryUtils.getTables(database);
	}
}
