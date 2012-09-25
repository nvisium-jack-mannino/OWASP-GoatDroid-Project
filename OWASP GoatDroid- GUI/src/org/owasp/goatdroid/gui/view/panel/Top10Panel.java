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
package org.owasp.goatdroid.gui.view.panel;

public class Top10Panel extends BaseViewPanel {

	static final String DIRECTORY = "top10";
	static final String RESOURCE_TYPE = "Top 10";
	static final boolean USE_SPECIFIC_APP_PATH = true;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Top10Panel() {
		super(DIRECTORY, RESOURCE_TYPE, USE_SPECIFIC_APP_PATH);
	}
}
