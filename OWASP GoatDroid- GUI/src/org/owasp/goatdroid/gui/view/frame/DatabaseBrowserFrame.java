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
package org.owasp.goatdroid.gui.view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTree;
import java.awt.Dimension;
import javax.swing.JTable;
import org.owasp.goatdroid.gui.ResourceTreeBuilder;
import org.owasp.goatdroid.gui.Utils;
import org.owasp.goatdroid.gui.database.DatabaseTreeBuilder;
import org.owasp.goatdroid.gui.database.QueryUtils;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

public class DatabaseBrowserFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel databasePanel;
	JSplitPane parentSplitPane;
	JSplitPane databaseTablesTasksSplitPane;
	JTabbedPane databaseTablesTabbedPane;
	JScrollPane databaseTablesScrollPane;
	JSplitPane databaseActivitySplitPane;
	JSplitPane databaseQuerySplitPane;
	JTabbedPane databaseQueryTabbedPane;
	JTextPane databaseQueryTextPane;
	JTabbedPane databaseResultsTabbedPane;
	JPanel databaseQueryButtonPanel;
	JButton databaseQueryButton;
	JTree databasesTree;
	DatabaseTreeBuilder treeBuilder;
	String currentDatabase = "";
	private JScrollPane databaseResultsScrollPane;
	private JPanel databaseInfoPanel;
	private JTextArea databaseInfoTextArea;

	/**
	 * Create the frame.
	 */
	public DatabaseBrowserFrame() {
		setTitle("Database Browser");

		setBounds(100, 100, 706, 484);
		databasePanel = new JPanel();
		databasePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(databasePanel);
		databasePanel.setLayout(new BorderLayout(0, 0));

		parentSplitPane = new JSplitPane();
		parentSplitPane.setResizeWeight(0.25);
		parentSplitPane.setContinuousLayout(true);
		parentSplitPane.setDividerSize(0);
		parentSplitPane.setBorder(null);
		databasePanel.add(parentSplitPane);

		databaseTablesTasksSplitPane = new JSplitPane();
		databaseTablesTasksSplitPane.setContinuousLayout(true);
		databaseTablesTasksSplitPane.setDividerSize(0);
		databaseTablesTasksSplitPane.setBorder(null);
		databaseTablesTasksSplitPane.setResizeWeight(0.95);
		databaseTablesTasksSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		parentSplitPane.setLeftComponent(databaseTablesTasksSplitPane);

		databaseTablesTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		databaseTablesTasksSplitPane.setLeftComponent(databaseTablesTabbedPane);

		databaseTablesScrollPane = new JScrollPane();
		databaseTablesScrollPane.setBorder(null);
		databaseTablesTabbedPane.addTab("Databases", null,
				databaseTablesScrollPane, null);
		treeBuilder = new DatabaseTreeBuilder();
		databaseTablesScrollPane.setViewportView(getDatabasesTree());

		databaseInfoPanel = new JPanel();
		databaseInfoPanel.setBackground(Color.WHITE);
		databaseInfoPanel.setBorder(null);
		databaseTablesTasksSplitPane.setRightComponent(databaseInfoPanel);
		databaseInfoPanel.setLayout(new BorderLayout(0, 0));

		databaseInfoTextArea = new JTextArea();
		databaseInfoTextArea.setText("Current Database: ");
		databaseInfoTextArea.setBorder(null);
		databaseInfoPanel.add(databaseInfoTextArea, BorderLayout.NORTH);

		databaseActivitySplitPane = new JSplitPane();
		databaseActivitySplitPane.setContinuousLayout(true);
		databaseActivitySplitPane.setDividerSize(0);
		databaseActivitySplitPane.setBorder(null);
		databaseActivitySplitPane.setResizeWeight(0.35);
		databaseActivitySplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		parentSplitPane.setRightComponent(databaseActivitySplitPane);

		databaseQuerySplitPane = new JSplitPane();
		databaseQuerySplitPane.setBorder(null);
		databaseQuerySplitPane.setContinuousLayout(true);
		databaseQuerySplitPane.setResizeWeight(0.85);
		databaseQuerySplitPane.setDividerSize(0);
		databaseQuerySplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		databaseActivitySplitPane.setLeftComponent(databaseQuerySplitPane);

		databaseQueryTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		databaseQuerySplitPane.setLeftComponent(databaseQueryTabbedPane);

		databaseQueryTextPane = new JTextPane();
		databaseQueryTextPane.setPreferredSize(new Dimension(2, 16));
		databaseQueryTabbedPane.addTab("Query", null, databaseQueryTextPane,
				null);

		databaseQueryButtonPanel = new JPanel();
		databaseQueryButtonPanel.setPreferredSize(new Dimension(10, 20));
		databaseQuerySplitPane.setRightComponent(databaseQueryButtonPanel);
		databaseQueryButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5,
				5));

		databaseQueryButton = new JButton("Execute Statement");
		databaseQueryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				try {
					String query = databaseQueryTextPane.getText();
					if (query.toLowerCase().startsWith("select"))
						queryTable(QueryUtils.executeSelectStatement(
								currentDatabase, query));
					else
						updateDBTable(currentDatabase, query);
				} catch (InstantiationException e) {
					updateTableErrors(e.getMessage());
				} catch (IllegalAccessException e) {
					updateTableErrors(e.getMessage());
				} catch (ClassNotFoundException e) {
					updateTableErrors(e.getMessage());
				} catch (SQLException e) {
					updateTableErrors(e.getMessage());
				}
			}
		});
		databaseQueryButtonPanel.add(databaseQueryButton);

		databaseResultsTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		databaseResultsTabbedPane.setAutoscrolls(true);
		databaseActivitySplitPane.setRightComponent(databaseResultsTabbedPane);

		databaseResultsScrollPane = new JScrollPane();

		databaseResultsTabbedPane.addTab("Results", null,
				databaseResultsScrollPane, null);
	}

	private JTree getDatabasesTree() {

		databasesTree = treeBuilder.buildTree();
		databasesTree
				.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
					public void valueChanged(
							javax.swing.event.TreeSelectionEvent e) {
						HashMap<String, String> currentResourceData = getSelectedResource(databasesTree);
						if (currentResourceData.get("resourceType").equals(
								"table")) {
							databaseQueryTextPane.setText("SELECT * FROM "
									+ currentResourceData.get("selected"));
						}
					}
				});
		return databasesTree;
	}

	public HashMap<String, String> getSelectedResource(JTree jTree) {

		HashMap<String, String> resourceData = new HashMap<String, String>();
		ResourceTreeBuilder tree = new ResourceTreeBuilder("databases", "");
		@SuppressWarnings("static-access")
		String path =  tree.getCurrentNodeParent(jTree);
		//ghetto hack
		path = path.replace(Utils.getFileSeparator(), "/");
		String[] directories = path.split("/");
		// offset of our desired value
		resourceData.put("selected",
				directories[directories.length - 1].toLowerCase());
		if (directories[directories.length - 2].toLowerCase().equals(
				"databases")) {
			currentDatabase = directories[directories.length - 1];
			databaseInfoTextArea.setText("Current Database: "
					+ directories[directories.length - 1]);
			resourceData.put("resourceType", "database");
		} else {
			currentDatabase = directories[directories.length - 2];
			resourceData.put("resourceType", "table");
		}
		return resourceData;
	}

	/*
	 * This is used under normal conditions where no errors are
	 * encountered...For "SELECT" statements
	 */
	public void queryTable(DefaultTableModel model) {
		JTable databaseResultsTable = new JTable(model);
		JTableHeader header = databaseResultsTable.getTableHeader();
		header.setBackground(Color.yellow);
		databaseResultsTable.setAutoResizeMode(0);
		for (int count = 0; count < databaseResultsTable.getColumnCount(); count++) {
			databaseResultsTable.getColumnModel().getColumn(count)
					.setPreferredWidth(200);
		}
		databaseResultsScrollPane.setViewportView(databaseResultsTable);
		databaseResultsScrollPane.validate();
	}

	/*
	 * We call this version when errors are encountered and we display only a
	 * single column with the error message
	 * 
	 * For "SELECT" statements
	 */
	public void updateTableErrors(String error) {
		String[] columnName = new String[1];
		columnName[0] = "ERROR";
		String[][] errorData = new String[1][1];
		errorData[0][0] = error;
		DefaultTableModel model = new DefaultTableModel(errorData, columnName);
		JTable databaseResultsTable = new JTable(model);
		databaseResultsTable.setAutoResizeMode(0);
		JTableHeader header = databaseResultsTable.getTableHeader();
		header.setBackground(Color.red);
		databaseResultsScrollPane.setViewportView(databaseResultsTable);
		databaseResultsScrollPane.validate();
	}

	/*
	 * Used for everything except "SELECT" statements
	 */
	public void updateDBTable(String database, String query)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		QueryUtils.executeUpdateStatement(database, query);
		JTextPane dbUpdateResultsTextPane = new JTextPane();
		dbUpdateResultsTextPane.setText("Successfully updated the database");
		databaseResultsScrollPane.setViewportView(dbUpdateResultsTextPane);
		databaseResultsScrollPane.validate();
	}

}
