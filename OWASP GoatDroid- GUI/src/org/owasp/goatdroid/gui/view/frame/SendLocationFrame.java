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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.Point;
import java.awt.Dimension;
import java.io.IOException;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import org.owasp.goatdroid.gui.Constants;
import org.owasp.goatdroid.gui.emulator.Emulator;

public class SendLocationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel sendLocationContentPane = null;
	private JLabel latitudeLabel;
	private JLabel longitudeLabel;
	private JTextField latitudeTextField = null;
	private JTextField longitudeTextField = null;
	private JButton sendLocationButton = null;

	/**
	 * This is the default constructor
	 */
	public SendLocationFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setResizable(false);
		this.setContentPane(getSendLocationContentPane());
		this.setTitle("Send Location");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSendLocationContentPane() {
		if (sendLocationContentPane == null) {
			longitudeLabel = new JLabel();
			longitudeLabel.setBounds(new Rectangle(49, 78, 64, 16));
			longitudeLabel.setText("Longitude");
			latitudeLabel = new JLabel();
			latitudeLabel.setBounds(new Rectangle(49, 32, 63, 16));
			latitudeLabel.setText("Latitude");
			sendLocationContentPane = new JPanel();
			sendLocationContentPane.setLayout(null);
			sendLocationContentPane.add(latitudeLabel, null);
			sendLocationContentPane.add(longitudeLabel, null);
			sendLocationContentPane.add(getLatitudeTextField(), null);
			sendLocationContentPane.add(getLongitudeTextField(), null);
			sendLocationContentPane.add(getSendLocationButton(), null);
		}
		return sendLocationContentPane;
	}

	/**
	 * This method initializes jLatitudeTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getLatitudeTextField() {
		if (latitudeTextField == null) {
			latitudeTextField = new JTextField();
			latitudeTextField.setSize(new Dimension(101, 16));
			latitudeTextField.setLocation(new Point(140, 32));
		}
		return latitudeTextField;
	}

	/**
	 * This method initializes jLongitudeTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getLongitudeTextField() {
		if (longitudeTextField == null) {
			longitudeTextField = new JTextField();
			longitudeTextField.setLocation(new Point(140, 78));
			longitudeTextField.setSize(new Dimension(101, 16));
		}
		return longitudeTextField;
	}

	/**
	 * This method initializes jSendLocationButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSendLocationButton() {
		if (sendLocationButton == null) {
			sendLocationButton = new JButton();
			sendLocationButton.setText("Send");
			sendLocationButton.setLocation(new Point(140, 124));
			sendLocationButton.setSize(new Dimension(75, 29));
			sendLocationButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							if (validateFields()) {
								try {
									Emulator.sendLocation(
											latitudeTextField.getText(),
											longitudeTextField.getText());
									closeFrame();
								} catch (SocketException e1) {
									showMessageDialog(Constants.EMULATOR_COMMS_FAILURE);
								} catch (IOException e1) {
									showMessageDialog(Constants.EMULATOR_COMMS_FAILURE);
								}
							}
						}
					});
		}
		return sendLocationButton;
	}

	public void closeFrame() {
		this.setVisible(false);
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public boolean validateFields() {

		if (!isFormatValid(latitudeTextField.getText(),
				Constants.LAT_LONG_REGEX)
				&& !isFormatValid(latitudeTextField.getText(),
						Constants.LAT_LONG_REGEX_WITH_NEGATIVE)) {
			showMessageDialog(Constants.LATITUDE_ERROR);
			return false;
		}

		if (!isFormatValid(longitudeTextField.getText(),
				Constants.LAT_LONG_REGEX)
				&& !isFormatValid(longitudeTextField.getText(),
						Constants.LAT_LONG_REGEX_WITH_NEGATIVE)) {
			showMessageDialog(Constants.LONGITUDE_ERROR);
			return false;
		}

		return true;

	}

	static public boolean isFormatValid(String input, String regexPattern) {

		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
}
