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

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JEditorPane;

public class AboutFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel aboutContentPane = null;
	private JEditorPane aboutEditorPane = null;

	/**
	 * This is the default constructor
	 */
	public AboutFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(350, 200);
		this.setResizable(false);
		this.setBackground(Color.white);
		this.setContentPane(getAboutContentPane());
		this.setTitle("About OWASP GoatDroid");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(null);
			aboutContentPane.add(getAboutEditorPane(), null);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes jAboutEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getAboutEditorPane() {
		if (aboutEditorPane == null) {
			aboutEditorPane = new JEditorPane();
			aboutEditorPane.setBounds(new Rectangle(5, 5, 310, 149));
			aboutEditorPane.setEditable(false);
			aboutEditorPane.setContentType("text/html");
			aboutEditorPane.setText("OWASP GoatDroid Project<p>"
					+ "Version: 0.9<p>" + "Developed By: Jack Mannino<p>"
					+ "For The: OWASP Mobile Security Project<p>"
					+ "Licensed Under: GPLv3");
		}
		return aboutEditorPane;
	}
}
