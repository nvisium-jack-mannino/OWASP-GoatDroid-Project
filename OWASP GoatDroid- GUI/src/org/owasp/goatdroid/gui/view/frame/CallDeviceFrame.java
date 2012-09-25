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

public class CallDeviceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel sendSMSContentPane = null;
	private JLabel phoneNumberLabel;
	private JTextField phoneNumberTextField = null;
	private JButton callDeviceButton = null;

	/**
	 * This is the default constructor
	 */
	public CallDeviceFrame() {
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
		this.setContentPane(getSendSMSContentPane());
		this.setTitle("Send SMS");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSendSMSContentPane() {
		if (sendSMSContentPane == null) {
			phoneNumberLabel = new JLabel();
			phoneNumberLabel.setBounds(new Rectangle(48, 57, 80, 34));
			phoneNumberLabel.setText("<html>From Phone<br>Number</html>");
			sendSMSContentPane = new JPanel();
			sendSMSContentPane.setLayout(null);
			sendSMSContentPane.add(phoneNumberLabel, null);
			sendSMSContentPane.add(getPhoneNumberTextField(), null);
			sendSMSContentPane.add(getCallDeviceButton(), null);
		}
		return sendSMSContentPane;
	}

	/**
	 * This method initializes jLatitudeTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getPhoneNumberTextField() {
		if (phoneNumberTextField == null) {
			phoneNumberTextField = new JTextField();
			phoneNumberTextField.setSize(new Dimension(101, 16));
			phoneNumberTextField.setLocation(new Point(141, 57));
		}
		return phoneNumberTextField;
	}

	/**
	 * This method initializes jSendLocationButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCallDeviceButton() {
		if (callDeviceButton == null) {
			callDeviceButton = new JButton();
			callDeviceButton.setText("Send");
			callDeviceButton.setLocation(new Point(109, 142));
			callDeviceButton.setSize(new Dimension(75, 29));
			callDeviceButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							if (validateFields()) {
								try {
									Emulator.callEmulator(phoneNumberTextField
											.getText());
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
		return callDeviceButton;
	}

	public void closeFrame() {
		this.setVisible(false);
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public boolean validateFields() {

		if (!isFormatValid(phoneNumberTextField.getText(),
				Constants.PHONE_NUMBER_REGEX)) {
			showMessageDialog(Constants.PHONE_NUMBER_ERROR);
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
