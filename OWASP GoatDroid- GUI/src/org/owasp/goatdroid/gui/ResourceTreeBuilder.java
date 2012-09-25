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
package org.owasp.goatdroid.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ResourceTreeBuilder {

	private static String path;
	private static String slash;
	private static String resourceType;

	public ResourceTreeBuilder(String resource, String resourceType) {

		ResourceTreeBuilder.setResourceType(resourceType);
		ResourceTreeBuilder.setSlash(Utils.getFileSeparator());
		ResourceTreeBuilder.setPath(Utils.getCurrentPath() + resource);
	}

	public JTree buildTree() {
		DefaultMutableTreeNode lessonNode = createNodes();
		JTree tree = new JTree(lessonNode);
		return tree;
	}

	public DefaultMutableTreeNode createNodes() {
		// this is the top node of our tree
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode(
				getResourceType());
		File lessonRoot = new File(getPath());

		try {
			// first we start with the categories
			for (File lessonCategory : lessonRoot.listFiles()) {
				// we should only look for folders to crawl through
				if (lessonCategory.isDirectory()) {
					DefaultMutableTreeNode lessonCategoryNode;
					String[] splitLessonCategory = lessonCategory.toString()
							.split(getSlash());
					lessonCategoryNode = new DefaultMutableTreeNode(
							splitLessonCategory[splitLessonCategory.length - 1]);
					// putting the categories into the topmost node
					topNode.add(lessonCategoryNode);
				}
			}
		}

		catch (NullPointerException e) {
			topNode = new DefaultMutableTreeNode("Error Populating Lessons"
					+ "");
		}
		return topNode;
	}

	static public File readFile(String filePath) {

		File file = new File(filePath);
		return file;
	}

	static public FileReader openFile(String fileName) {

		FileReader fileStream;

		try {

			File file = new File(fileName);
			fileStream = new FileReader(file);
			return fileStream;
		}

		catch (FileNotFoundException e) {
			fileStream = null;
			return fileStream;
		}
	}

	static public BufferedReader generateBufferedReader(String fileName) {

		FileReader fileReader = openFile(fileName);
		try {
			BufferedReader buffer = new BufferedReader(fileReader);
			return buffer;
		} catch (NullPointerException e) {
			return null;
		}
		// return buffer;
	}

	static public String getAppPath(String lessonPath) {

		BufferedReader reader = generateBufferedReader(lessonPath + getSlash()
				+ "app_path.txt");
		String appPath = "";
		try {
			return appPath = reader.readLine();
		} catch (IOException e) {
			return appPath;
		}
	}

	static public String getCurrentNodeParent(JTree tree) {
		try {

			String currentPath = tree.getLastSelectedPathComponent().toString();
			Object[] paths = tree.getSelectionPath().getPath();
			if (!currentPath.matches(paths[1].toString()))
				return path + getSlash() + paths[1].toString() + getSlash()
						+ currentPath;
			else
				return path + getSlash() + paths[1].toString();
		}
		/*
		 * This only fires when you re-request the top node easy enough to catch
		 */
		catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}

	static public String buildAPKPath(String appName) {
		return path + getSlash() + appName;
	}

	static public Boolean isLessonDirectory(String filePath) {
		File file = new File(filePath + getSlash() + "lesson_info.html");
		if (file.exists())
			return true;
		else
			return false;
	}

	static public String createLessonInfoURL(JTree tree) {
		String currentNodePath = getCurrentNodeParent(tree);
		if (Utils.getOS().startsWith("windows"))
			return "file:" + slash + File.separator
					+ getCurrentNodeParent(tree) + File.separator
					+ "lesson_info.html";
		else
			return "file:" + currentNodePath + getSlash() + "lesson_info.html";
	}

	static public String getCurrentSelection(JTree tree) {

		return tree.getLastSelectedPathComponent().toString();
	}

	public static String getSlash() {
		return slash;
	}

	public static void setSlash(String slash) {
		ResourceTreeBuilder.slash = slash;
	}

	public static String getResourceType() {
		return resourceType;
	}

	public static void setResourceType(String resourceType) {
		ResourceTreeBuilder.resourceType = resourceType;
	}

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		ResourceTreeBuilder.path = path;
	}
}
