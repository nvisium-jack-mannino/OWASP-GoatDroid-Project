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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JButton;
import org.owasp.goatdroid.gui.Constants;
import org.owasp.goatdroid.gui.config.Config;
import org.owasp.goatdroid.gui.exception.CorruptConfigException;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.swtdesigner.FocusTraversalOnArray;
import java.awt.Component;

public class ConfigureEditFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel configureEditContentPane = null;
	private JTabbedPane configureEditTabbedPane = null;
	private JPanel androidSettingsPanel = null;
	private JLabel androidSDKPathLabel;
	private JLabel androidVirtualDevicesPathLabel;
	private JLabel androidProxyHostLabel;
	private JLabel androidProxyPortLabel;
	private JTextArea androidSDKPathTextArea = null;
	private JTextArea androidVirtualDevicesTextArea = null;
	private JTextField androidProxyHostTextField = null;
	private JTextField androidProxyPortTextField = null;
	private JTextField androidEmulatorScreenSizeTextField = null;
	private JButton androidSDKPathButton = null;
	private JButton androidVirtualDevicesButton = null;
	private JButton androidSettingsUpdateButton = null;
	private HashMap<String, String> settings;
	private JPanel webServiceSettingsPanel;
	private JLabel webServiceHTTPPortLabel;
	private JTextField webServiceHTTPPortTextField;
	private JButton webServicesUpdateSettingsButton;
	private JLabel webServiceHTTPSPortLabel;
	private JTextField webServiceHTTPSPortTextField;

	/**
	 * This is the default constructor
	 * 
	 * @throws CorruptConfigException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public ConfigureEditFrame() throws FileNotFoundException, IOException,
			CorruptConfigException {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws CorruptConfigException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void initialize() throws FileNotFoundException, IOException,
			CorruptConfigException {
		settings = Config.readConfig();
		this.setSize(600, 400);
		this.setContentPane(getConfigureEditContentPane());
		this.setTitle("Edit Configuration");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getConfigureEditContentPane() {
		if (configureEditContentPane == null) {
			configureEditContentPane = new JPanel();
			configureEditContentPane.setLayout(new BorderLayout());
			configureEditContentPane.add(getConfigureEditTabbedPane(),
					BorderLayout.NORTH);
		}
		return configureEditContentPane;
	}

	/**
	 * This method initializes jConfigureEditTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getConfigureEditTabbedPane() {
		if (configureEditTabbedPane == null) {
			configureEditTabbedPane = new JTabbedPane();
			configureEditTabbedPane.setPreferredSize(new Dimension(482, 375));
			configureEditTabbedPane.addTab("Android", null,
					getAndroidSettingsPanel(), null);
			configureEditTabbedPane.addTab("Web Services", null,
					getWebServiceSettingsPanel(), null);
			configureEditTabbedPane
					.setFocusTraversalPolicy(new FocusTraversalOnArray(
							new Component[] { getAndroidProxyHostTextField(),
									getAndroidProxyPortTextField(),
									getAndroidEmulatorScreenSizeTextField(),
									getAndroidVirtualDevicesButton(),
									getAndroidSDKPathButton(),
									getAndroidSettingsUpdateButton() }));
		}
		return configureEditTabbedPane;
	}

	/**
	 * This method initializes jAndroidSettingsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAndroidSettingsPanel() {
		if (androidSettingsPanel == null) {
			androidProxyPortLabel = new JLabel();
			androidProxyPortLabel.setText("Proxy Port");
			androidProxyPortLabel.setLocation(new Point(69, 77));
			androidProxyPortLabel.setSize(new Dimension(69, 36));
			androidProxyHostLabel = new JLabel();
			androidProxyHostLabel.setText("Proxy Host");
			androidProxyHostLabel.setLocation(new Point(69, 30));
			androidProxyHostLabel.setSize(new Dimension(75, 35));
			androidVirtualDevicesPathLabel = new JLabel();
			androidVirtualDevicesPathLabel.setText("Virtual Devices Path");
			androidVirtualDevicesPathLabel.setLocation(new Point(6, 176));
			androidVirtualDevicesPathLabel.setSize(new Dimension(138, 16));
			androidSDKPathLabel = new JLabel();
			androidSDKPathLabel.setText("SDK Path");
			androidSDKPathLabel.setLocation(new Point(69, 262));
			androidSDKPathLabel.setSize(new Dimension(75, 16));
			androidSettingsPanel = new JPanel();
			androidSettingsPanel.setLayout(null);
			androidSettingsPanel.add(androidSDKPathLabel, null);
			androidSettingsPanel.add(androidVirtualDevicesPathLabel, null);
			androidSettingsPanel.add(androidProxyHostLabel, null);
			androidSettingsPanel.add(androidProxyPortLabel, null);
			androidSettingsPanel.add(getAndroidSDKPathTextArea(), null);
			androidSettingsPanel.add(getAndroidVirtualDevicesTextArea(), null);
			androidSettingsPanel.add(getAndroidProxyHostTextField(), null);
			androidSettingsPanel.add(getAndroidProxyPortTextField(), null);
			androidSettingsPanel.add(getAndroidSDKPathButton(), null);
			androidSettingsPanel.add(getAndroidVirtualDevicesButton(), null);
			androidSettingsPanel.add(getAndroidSettingsUpdateButton(), null);
			JLabel androidEmulatorScreenSizeLabel = new JLabel(
					"<html>Emulator Screen Size<br>(1-3 or leave blank or zero for default)</html>");
			androidEmulatorScreenSizeLabel.setBounds(340, 30, 90, 83);
			androidSettingsPanel.add(androidEmulatorScreenSizeLabel);
			androidSettingsPanel.add(getAndroidEmulatorScreenSizeTextField(),
					null);
			androidSettingsPanel
					.setFocusTraversalPolicy(new FocusTraversalOnArray(
							new Component[] { getAndroidProxyHostTextField(),
									getAndroidProxyPortTextField(),
									getAndroidEmulatorScreenSizeTextField(),
									getAndroidVirtualDevicesButton(),
									getAndroidSDKPathButton(),
									getAndroidSettingsUpdateButton() }));
		}
		return androidSettingsPanel;
	}

	/**
	 * This method initializes jAndroidSDKPathTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextArea getAndroidSDKPathTextArea() {
		if (androidSDKPathTextArea == null) {
			androidSDKPathTextArea = new JTextArea();
			androidSDKPathTextArea.setSize(new Dimension(181, 74));
			androidSDKPathTextArea.setLocation(new Point(156, 234));
			androidSDKPathTextArea.setText(settings.get("sdk_path"));
			androidSDKPathTextArea.setEditable(false);
			androidSDKPathTextArea.setLineWrap(true);
		}
		return androidSDKPathTextArea;
	}

	/**
	 * This method initializes jAndroidVirtualDevicesTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextArea getAndroidVirtualDevicesTextArea() {
		if (androidVirtualDevicesTextArea == null) {
			androidVirtualDevicesTextArea = new JTextArea();
			androidVirtualDevicesTextArea.setLocation(new Point(156, 149));
			androidVirtualDevicesTextArea.setSize(new Dimension(181, 74));
			androidVirtualDevicesTextArea.setLineWrap(true);
			androidVirtualDevicesTextArea.setText(settings.get("devices_path"));
			androidVirtualDevicesTextArea.setEditable(false);
		}
		return androidVirtualDevicesTextArea;
	}

	/**
	 * This method initializes jAndroidProxyHostTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAndroidProxyHostTextField() {
		if (androidProxyHostTextField == null) {
			androidProxyHostTextField = new JTextField();
			androidProxyHostTextField.setLocation(new Point(164, 30));
			androidProxyHostTextField.setSize(new Dimension(124, 34));
			androidProxyHostTextField.setText(settings.get("proxy_host"));
		}
		return androidProxyHostTextField;
	}

	/**
	 * This method initializes jAndroidProxyPortTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAndroidProxyPortTextField() {
		if (androidProxyPortTextField == null) {
			androidProxyPortTextField = new JTextField();
			androidProxyPortTextField.setLocation(new Point(164, 78));
			androidProxyPortTextField.setSize(new Dimension(124, 34));
			androidProxyPortTextField.setText(settings.get("proxy_port"));
		}
		return androidProxyPortTextField;
	}

	/**
	 * This method initializes jAndroidSDKPathButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAndroidSDKPathButton() {
		if (androidSDKPathButton == null) {
			androidSDKPathButton = new JButton();
			androidSDKPathButton.setText("Browse");
			androidSDKPathButton.setLocation(new Point(355, 257));
			androidSDKPathButton.setSize(new Dimension(75, 29));
			androidSDKPathButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							String fileName = openFileDialog();
							androidSDKPathTextArea.setText(fileName);
						}
					});
		}
		return androidSDKPathButton;
	}

	/**
	 * This method initializes jAndroidVirtualDevicesButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAndroidVirtualDevicesButton() {
		if (androidVirtualDevicesButton == null) {
			androidVirtualDevicesButton = new JButton();
			androidVirtualDevicesButton.setText("Browse");
			androidVirtualDevicesButton.setLocation(new Point(355, 171));
			androidVirtualDevicesButton.setSize(new Dimension(75, 29));
			androidVirtualDevicesButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							String fileName = openFileDialog();
							androidVirtualDevicesTextArea.setText(fileName);
						}
					});
		}
		return androidVirtualDevicesButton;
	}

	/**
	 * This method initializes jAndroidSettingsUpdateButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAndroidSettingsUpdateButton() {
		if (androidSettingsUpdateButton == null) {
			androidSettingsUpdateButton = new JButton();
			androidSettingsUpdateButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			androidSettingsUpdateButton.setText("Update Settings");
			androidSettingsUpdateButton.setSize(new Dimension(138, 29));
			androidSettingsUpdateButton.setLocation(new Point(435, 294));
			androidSettingsUpdateButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							try {
								if (validateAndroidFields()) {
									Config.updateAndroidSettings(
											androidSDKPathTextArea.getText(),
											androidVirtualDevicesTextArea
													.getText(),
											androidProxyHostTextField.getText(),
											androidProxyPortTextField.getText(),
											androidEmulatorScreenSizeTextField
													.getText());
									Config.updateWebServiceSettings(
											webServiceHTTPPortTextField
													.getText(),
											webServiceHTTPSPortTextField
													.getText());
									showConfigSuccessDialog();
								} else {
								}

							} catch (FileNotFoundException e1) {
								showConfigFailureDialog();
							} catch (IOException e1) {
								showConfigFailureDialog();
							} catch (CorruptConfigException e1) {
								showConfigFailureDialog();
							}
						}
					});
		}
		return androidSettingsUpdateButton;
	}

	public void showConfigSuccessDialog() {
		JOptionPane.showMessageDialog(this, Constants.SETTINGS_UPDATED_SUCCESS);
	}

	public void showConfigFailureDialog() {
		JOptionPane.showMessageDialog(this, Constants.SETTINGS_UPDATE_FAILED);
	}

	public void showDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private String openFileDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setFileHidingEnabled(false);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file.toString();
		} else {
			return "";
		}
	}

	private JPanel getWebServiceSettingsPanel() {
		if (webServiceSettingsPanel == null) {
			webServiceSettingsPanel = new JPanel();
			webServiceSettingsPanel.setLayout(null);
			webServiceSettingsPanel.add(getWebServiceHTTPPortLabel());
			webServiceSettingsPanel.add(getWebServiceHTTPPortTextField());
			webServiceSettingsPanel.add(getWebServicesUpdateSettingsButton());
			webServiceSettingsPanel.add(getWebServiceHTTPSPortLabel());
			webServiceSettingsPanel.add(getWebServiceHTTPSPortTextField());
		}
		return webServiceSettingsPanel;
	}

	private JLabel getWebServiceHTTPPortLabel() {
		if (webServiceHTTPPortLabel == null) {
			webServiceHTTPPortLabel = new JLabel(
					"<html>HTTP Port<br>(Default:8888)</html>");
			webServiceHTTPPortLabel.setBounds(138, 50, 134, 32);
		}
		return webServiceHTTPPortLabel;
	}

	private JTextField getWebServiceHTTPPortTextField() {
		if (webServiceHTTPPortTextField == null) {
			webServiceHTTPPortTextField = new JTextField();
			webServiceHTTPPortTextField.setText(settings
					.get("web_service_http_port"));
			webServiceHTTPPortTextField.setBounds(284, 48, 134, 34);
		}
		return webServiceHTTPPortTextField;
	}

	private JLabel getWebServiceHTTPSPortLabel() {
		if (webServiceHTTPSPortLabel == null) {
			webServiceHTTPSPortLabel = new JLabel(
					"<html>HTTPS Port<br>(Default:9888)</html>");
			webServiceHTTPSPortLabel.setBounds(138, 127, 106, 34);
		}
		return webServiceHTTPSPortLabel;
	}

	private JTextField getWebServiceHTTPSPortTextField() {
		if (webServiceHTTPSPortTextField == null) {
			webServiceHTTPSPortTextField = new JTextField();
			webServiceHTTPSPortTextField.setText(settings
					.get("web_service_https_port"));
			webServiceHTTPSPortTextField.setBounds(284, 127, 134, 34);
		}
		return webServiceHTTPSPortTextField;
	}

	private JTextField getAndroidEmulatorScreenSizeTextField() {
		if (androidEmulatorScreenSizeTextField == null) {
			androidEmulatorScreenSizeTextField = new JTextField();
			androidEmulatorScreenSizeTextField.setText(settings
					.get("emulator_screen_size"));
			androidEmulatorScreenSizeTextField.setSize(new Dimension(124, 34));
			androidEmulatorScreenSizeTextField.setLocation(new Point(164, 30));
			androidEmulatorScreenSizeTextField.setBounds(442, 55, 124, 34);
		}
		return androidEmulatorScreenSizeTextField;

	}

	private JButton getWebServicesUpdateSettingsButton() {
		if (webServicesUpdateSettingsButton == null) {
			webServicesUpdateSettingsButton = new JButton("Update Settings");
			webServicesUpdateSettingsButton
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
						}
					});
			webServicesUpdateSettingsButton
					.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							try {
								if (validateWebServiceFields()) {
									Config.updateWebServiceSettings(
											webServiceHTTPPortTextField
													.getText(),
											webServiceHTTPSPortTextField
													.getText());
									Config.updateAndroidSettings(
											androidSDKPathTextArea.getText(),
											androidVirtualDevicesTextArea
													.getText(),
											androidProxyHostTextField.getText(),
											androidProxyPortTextField.getText(),
											androidEmulatorScreenSizeTextField
													.getText());
									showConfigSuccessDialog();
								}
							} catch (FileNotFoundException e1) {
								showConfigFailureDialog();
							} catch (IOException e1) {
								showConfigFailureDialog();
							} catch (CorruptConfigException e1) {
								showConfigFailureDialog();
							}

						}
					});
			webServicesUpdateSettingsButton.setBounds(199, 197, 181, 29);
		}
		return webServicesUpdateSettingsButton;
	}

	public boolean validateAndroidFields() {

		/*
		 * We only attempt to validate these if the user elects to use this
		 * feature..otherwise, we move on
		 */
		if (!androidProxyHostTextField.getText().equals("")
				|| !androidProxyPortTextField.getText().equals("")) {
			if (!isFormatValid(androidProxyHostTextField.getText(),
					Constants.IP_REGEX)) {
				showDialog(Constants.IP_FORMAT_ERROR);
				return false;
			}

			if (!isFormatValid(androidProxyPortTextField.getText(),
					Constants.PORT_REGEX)) {
				showDialog(Constants.PORT_FORMAT_ERROR);
				return false;
			}
		}

		/*
		 * We only check this if the user set it
		 */
		if (!androidEmulatorScreenSizeTextField.getText().equals("")) {
			if (!isFormatValid(androidEmulatorScreenSizeTextField.getText(),
					Constants.EMULATOR_SCREEN_SIZE_REGEX)) {
				showDialog(Constants.EMULATOR_SCREEN_SIZE_ERROR);
				return false;
			}
		}

		return true;
	}

	public boolean validateWebServiceFields() {

		if (!isFormatValid(webServiceHTTPPortTextField.getText(),
				Constants.PORT_REGEX)) {
			showDialog(Constants.PORT_FORMAT_ERROR);
			return false;
		}

		if (!isFormatValid(webServiceHTTPSPortTextField.getText(),
				Constants.PORT_REGEX)) {
			showDialog(Constants.PORT_FORMAT_ERROR);
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
