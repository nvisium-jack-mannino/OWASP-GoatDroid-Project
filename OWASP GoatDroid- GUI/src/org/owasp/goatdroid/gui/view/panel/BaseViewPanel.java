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

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import org.owasp.goatdroid.gui.ResourceTreeBuilder;
import org.owasp.goatdroid.gui.Constants;
import org.owasp.goatdroid.gui.config.Config;
import org.owasp.goatdroid.gui.emulator.Emulator;
import org.owasp.goatdroid.gui.emulator.EmulatorWorker;
import org.owasp.goatdroid.gui.exception.CorruptConfigException;
import org.owasp.goatdroid.gui.webservice.WebService;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BaseViewPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSplitPane homeAppsViewSplitPane = null;
	private JScrollPane homeAppsViewSelectorScrollPane = null;
	private JScrollPane homeAppsWorkAreaScrollPane = null;
	private JTabbedPane homeAppsSolutionsTabbedPane = null;
	private JPanel homeAppsInfoTab = null;
	private JTree appsTree = null;
	private JEditorPane homeAppsInfoEditorPane = null;
	private JSplitPane homeAppsInfoSplitPane = null;
	private JPanel homeAppsExecutionPanel = null;
	private JButton homeAppsStartWebServiceButton = null;
	private JButton homeAppsStartInEmulatorButton = null;
	static private Config config;
	private JButton homeAppsPushAppButton = null;
	WebService service;
	private ResourceTreeBuilder treeBuilder;
	String resourceType;
	String lessonDirectory;
	/*
	 * This lets us choose whether to tie a specific app to a specific lesson.
	 * All we have to do is include the app_path file within a lesson's root
	 * directory
	 */
	private boolean useSpecificAppPath;

	/**
	 * Create the panel.
	 */
	public BaseViewPanel(String directory, String resourceType,
			boolean useSpecificAppPath) {
		treeBuilder = new ResourceTreeBuilder(directory, resourceType);
		this.resourceType = resourceType;
		this.useSpecificAppPath = useSpecificAppPath;
		this.lessonDirectory = directory;
		initialize();
	}

	public void initialize() {
		this.setPreferredSize(new Dimension(0, 0));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLayout(new BorderLayout(0, 0));
		add(getHomeAppsViewSplitPane());
		config = new Config();
	}

	private JSplitPane getHomeAppsViewSplitPane() {
		if (homeAppsViewSplitPane == null) {
			homeAppsViewSplitPane = new JSplitPane();
			homeAppsViewSplitPane.setDividerSize(0);
			homeAppsViewSplitPane.setEnabled(false);
			homeAppsViewSplitPane.setDividerLocation(200);
			homeAppsViewSplitPane.setContinuousLayout(true);
			homeAppsViewSplitPane.setResizeWeight(0.1);
			homeAppsViewSplitPane.setPreferredSize(new Dimension(0, 0));
			homeAppsViewSplitPane
					.setLeftComponent(getHomeAppsViewSelectorScrollPane());
			homeAppsViewSplitPane
					.setRightComponent(getHomeAppsWorkAreaScrollPane());
			homeAppsViewSplitPane.setOneTouchExpandable(true);

		}
		return homeAppsViewSplitPane;
	}

	private JScrollPane getHomeAppsViewSelectorScrollPane() {
		if (homeAppsViewSelectorScrollPane == null) {
			homeAppsViewSelectorScrollPane = new JScrollPane();
			homeAppsViewSelectorScrollPane.setPreferredSize(new Dimension(800,
					512));
			homeAppsViewSelectorScrollPane.setViewportView(getAppsTree());
		}
		return homeAppsViewSelectorScrollPane;
	}

	private JScrollPane getHomeAppsWorkAreaScrollPane() {
		if (homeAppsWorkAreaScrollPane == null) {
			homeAppsWorkAreaScrollPane = new JScrollPane();
			homeAppsWorkAreaScrollPane
					.setViewportView(getHomeAppsSolutionsTabbedPane());
		}
		return homeAppsWorkAreaScrollPane;
	}

	private JTabbedPane getHomeAppsSolutionsTabbedPane() {
		if (homeAppsSolutionsTabbedPane == null) {
			homeAppsSolutionsTabbedPane = new JTabbedPane();
			homeAppsSolutionsTabbedPane.addTab("Description", null,
					getHomeAppsInfoTab(), null);
		}
		return homeAppsSolutionsTabbedPane;
	}

	private JPanel getHomeAppsInfoTab() {
		if (homeAppsInfoTab == null) {
			homeAppsInfoTab = new JPanel();
			homeAppsInfoTab.setBackground(Color.white);
			homeAppsInfoTab.setPreferredSize(new Dimension(457, 75));
			homeAppsInfoTab.setLayout(new BorderLayout(0, 0));
			homeAppsInfoTab.add(getHomeAppsInfoSplitPane());
		}
		return homeAppsInfoTab;
	}

	private JTree getAppsTree() {
		if (appsTree == null) {
			appsTree = treeBuilder.buildTree();
			appsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
					try {
						String lessonInfoURL = ResourceTreeBuilder
								.createLessonInfoURL(appsTree);
						homeAppsInfoEditorPane.setPage(lessonInfoURL);
						homeAppsStartInEmulatorButton.setVisible(true);
						homeAppsPushAppButton.setVisible(true);
						homeAppsStartWebServiceButton.setVisible(true);

						try {
							if (!WebService.isServiceRunning())
								setDisplayWebServiceStopped();
							else
								setDisplayWebServiceStarted();
						} catch (NullPointerException e1) {
							service = new WebService(appsTree);
						}

					} catch (IOException e2) {
						e2.getMessage();
					}
				}
			});
		}
		return appsTree;
	}

	private JEditorPane getHomeAppsInfoEditorPane() {
		if (homeAppsInfoEditorPane == null) {
			homeAppsInfoEditorPane = new JEditorPane();
			homeAppsInfoEditorPane.setEditable(false);
			homeAppsInfoEditorPane.setPreferredSize(new Dimension(500, 585));
			homeAppsInfoEditorPane.setContentType("text/html");

		}
		return homeAppsInfoEditorPane;
	}

	private JSplitPane getHomeAppsInfoSplitPane() {
		if (homeAppsInfoSplitPane == null) {
			homeAppsInfoSplitPane = new JSplitPane();
			homeAppsInfoSplitPane.setContinuousLayout(true);
			homeAppsInfoSplitPane.setDividerSize(0);
			homeAppsInfoSplitPane.setBorder(null);
			homeAppsInfoSplitPane.setOpaque(false);
			homeAppsInfoSplitPane.setMaximumSize(new Dimension(244, 33));
			homeAppsInfoSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			homeAppsInfoSplitPane.setTopComponent(getHomeAppsExecutionPanel());
			homeAppsInfoSplitPane
					.setBottomComponent(getHomeAppsInfoEditorPane());
		}
		return homeAppsInfoSplitPane;
	}

	private JPanel getHomeAppsExecutionPanel() {
		if (homeAppsExecutionPanel == null) {
			homeAppsExecutionPanel = new JPanel();
			homeAppsExecutionPanel.setBackground(Color.WHITE);
			homeAppsExecutionPanel.setSize(new Dimension(40, 30));
			homeAppsExecutionPanel.setMaximumSize(new Dimension(40, 30));
			homeAppsExecutionPanel.setMinimumSize(new Dimension(40, 30));
			homeAppsExecutionPanel.setPreferredSize(new Dimension(40, 30));
			homeAppsExecutionPanel.setLayout(new GridLayout(0, 3, 0, 0));
			homeAppsExecutionPanel.add(getHomeAppsStartInEmulatorButton());
			homeAppsExecutionPanel.add(getHomeAppsPushAppButton());
			homeAppsExecutionPanel.add(getHomeAppsStartWebServiceButton());
		}
		return homeAppsExecutionPanel;
	}

	private JButton getHomeAppsStartWebServiceButton() {
		if (homeAppsStartWebServiceButton == null) {
			homeAppsStartWebServiceButton = new JButton();
			homeAppsStartWebServiceButton.setMargin(new Insets(0, 0, 0, 0));
			homeAppsStartWebServiceButton.setMinimumSize(new Dimension(75, 3));
			homeAppsStartWebServiceButton.setMaximumSize(new Dimension(75, 3));
			homeAppsStartWebServiceButton
					.setPreferredSize(new Dimension(75, 3));
			homeAppsStartWebServiceButton.setText("Start Web Service");
			homeAppsStartWebServiceButton.setForeground(Color.black);
			homeAppsStartWebServiceButton.setBackground(Color.WHITE);
			homeAppsStartWebServiceButton.setVisible(false);
			homeAppsStartWebServiceButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						@SuppressWarnings("static-access")
						public void mouseReleased(java.awt.event.MouseEvent e) {
							try {/*
								 * This checks to ensure there is a selected
								 * resource
								 */
								if (!resourceType.equals(treeBuilder
										.getCurrentSelection(appsTree))) {
									if (homeAppsStartWebServiceButton.getText()
											.startsWith("Start")) {
										if (!(config.getWebServiceHTTPPort()
												.equals("") || config
												.getWebServiceHTTPSPort()
												.equals(""))) {
											/*
											 * If it's null, we initialize it
											 * else, we simply stop the service.
											 */
											try {
												if (service.isServiceRunning())
													service.stopService();
											} catch (NullPointerException e1) {
												/*
												 * We just catch it here. If the
												 * service doesn't exist it gets
												 * created below
												 */
											}
											/*
											 * We reinitialize the service to
											 * pass in the currently selected
											 * application
											 */
											service = new WebService(appsTree);
											service.startService(true);
											setDisplayWebServiceStarted();
										} else {
											showMessageDialog(Constants.NO_WEB_PORTS_CONFIGURED);
										}
									} else {
										service.stopService();
										setDisplayWebServiceStopped();
									}
								} else {
									showMessageDialog(Constants.SELECT_AN_APP);
								}
							} catch (Exception e2) {
							}
						}
					});
		}
		return homeAppsStartWebServiceButton;
	}

	private JButton getHomeAppsStartInEmulatorButton() {
		if (homeAppsStartInEmulatorButton == null) {
			homeAppsStartInEmulatorButton = new JButton();
			homeAppsStartInEmulatorButton.setBackground(Color.WHITE);
			homeAppsStartInEmulatorButton.setMargin(new Insets(0, 0, 0, 0));
			homeAppsStartInEmulatorButton
					.setPreferredSize(new Dimension(75, 3));
			homeAppsStartInEmulatorButton.setMinimumSize(new Dimension(75, 3));
			homeAppsStartInEmulatorButton.setMaximumSize(new Dimension(75, 3));
			homeAppsStartInEmulatorButton.setText("Start Emulator");
			homeAppsStartInEmulatorButton.setVisible(false);
			homeAppsStartInEmulatorButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							try {
								if (!resourceType.equals(ResourceTreeBuilder
										.getCurrentSelection(appsTree))) {
									Emulator emulator = new Emulator();
									@SuppressWarnings("static-access")
									String devicePath = config
											.getVirtualDevicesPath();
									if (devicePath.equals(""))
										showMessageDialog(Constants.SPECIFY_SDK_LOCATION);
									else if (!emulator.isPathValid(devicePath)) {
										showMessageDialog(Constants.INVALID_AVD_PATH);
									} else {

										@SuppressWarnings("static-access")
										ArrayList<String> devices = emulator
												.getVirtualDevices(devicePath);
										if (devices.size() > 0) {
											String[] deviceArray = new String[devices
													.size()];
											deviceArray = devices
													.toArray(deviceArray);
											String selection = (String) JOptionPane
													.showInputDialog(
															null,
															"Please select your device",
															"Device Selector",
															JOptionPane.QUESTION_MESSAGE,
															null, deviceArray,
															deviceArray[0]);
											EmulatorWorker task = new EmulatorWorker(
													"startEmulator", selection,
													"", "", BaseViewPanel.this);
											task.addPropertyChangeListener(new PropertyChangeListener() {
												public void propertyChange(
														PropertyChangeEvent evt) {
													if ("progress".equals(evt
															.getPropertyName())) {

														showMessageDialog((String) evt
																.getNewValue());
													}
												}
											});
											task.execute();
										} else {
											showMessageDialog(Constants.NO_ANDROID_VIRTUAL_DEVICES);
										}
									}
								} else {
									showMessageDialog(Constants.SELECT_AN_APP);
								}
							} catch (FileNotFoundException e1) {
								e1.getMessage();
							} catch (IOException e1) {
								e1.getMessage();
							} catch (CorruptConfigException e1) {
								e1.getMessage();
							}
						}
					});
		}
		return homeAppsStartInEmulatorButton;
	}

	private JButton getHomeAppsPushAppButton() {
		if (homeAppsPushAppButton == null) {
			homeAppsPushAppButton = new JButton();
			homeAppsPushAppButton.setBackground(Color.WHITE);
			homeAppsPushAppButton.setMargin(new Insets(0, 0, 0, 0));
			homeAppsPushAppButton.setPreferredSize(new Dimension(75, 3));
			homeAppsPushAppButton.setMinimumSize(new Dimension(75, 3));
			homeAppsPushAppButton.setMaximumSize(new Dimension(75, 3));
			homeAppsPushAppButton.setText("Push App To Device");
			homeAppsPushAppButton.setVisible(false);
			homeAppsPushAppButton
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {

							try {
								if (!resourceType.equals(ResourceTreeBuilder
										.getCurrentSelection(appsTree))) {
									String devicePath = Config
											.getVirtualDevicesPath();
									if (devicePath.equals("")) {
										showMessageDialog(Constants.SPECIFY_SDK_LOCATION);
									} else {
										if (!useSpecificAppPath) {
											String appPath = Emulator
													.getAppPath(appsTree, true,
															"", lessonDirectory);

											if (appPath.equals(""))
												showMessageDialog(Constants.APP_PUSH_FAILED);
											else {
												if (Emulator
														.isAndroidDeviceAvailable()) {
													String[] deviceArray = Emulator
															.getAvailableAndroidDevices();
													if (deviceArray.length == 1) {
														showMessageDialog(Constants.PUSHING_APP);
														EmulatorWorker task = new EmulatorWorker(
																"pushAppOntoDevice",
																"",
																"",
																appPath,
																BaseViewPanel.this);
														task.addPropertyChangeListener(new PropertyChangeListener() {
															public void propertyChange(
																	PropertyChangeEvent evt) {
																if ("progress"
																		.equals(evt
																				.getPropertyName())) {
																	showMessageDialog((String) evt
																			.getNewValue());
																}
															}
														});
														task.execute();
													} else {
														String selection = (String) JOptionPane
																.showInputDialog(
																		null,
																		"Please select your device",
																		"Device Selector",
																		JOptionPane.QUESTION_MESSAGE,
																		null,
																		deviceArray,
																		deviceArray[0]);
														showMessageDialog(Constants.PUSHING_APP);
														EmulatorWorker task = new EmulatorWorker(
																"pushAppOntoDevice",
																"",
																selection,
																appPath,
																BaseViewPanel.this);
														task.addPropertyChangeListener(new PropertyChangeListener() {
															public void propertyChange(
																	PropertyChangeEvent evt) {
																if ("progress"
																		.equals(evt
																				.getPropertyName())) {
																	showMessageDialog((String) evt
																			.getNewValue());
																}
															}
														});
														task.execute();
													}
												} else {
													showMessageDialog(Constants.NO_ANDROID_DEVICES_AVAILABLE);
												}
											}
										} else {
											try {
												String appPath = Emulator
														.getAppPath(
																appsTree,
																true,
																ResourceTreeBuilder
																		.getAppPath(ResourceTreeBuilder
																				.getCurrentNodeParent(appsTree)),
																lessonDirectory);
												if (appPath.equals(""))
													showMessageDialog(Constants.APP_PUSH_FAILED);
												else {
													if (Emulator
															.isAndroidDeviceAvailable()) {
														String[] deviceArray = Emulator
																.getAvailableAndroidDevices();
														if (deviceArray.length == 1) {
															showMessageDialog(Constants.PUSHING_APP);
															EmulatorWorker task = new EmulatorWorker(
																	"pushAppOntoDevice",
																	"",
																	"",
																	appPath,
																	BaseViewPanel.this);
															task.addPropertyChangeListener(new PropertyChangeListener() {
																public void propertyChange(
																		PropertyChangeEvent evt) {
																	if ("progress"
																			.equals(evt
																					.getPropertyName())) {
																		showMessageDialog((String) evt
																				.getNewValue());
																	}
																}
															});
															task.execute();
														} else {
															String selection = (String) JOptionPane
																	.showInputDialog(
																			null,
																			"Please select your device",
																			"Device Selector",
																			JOptionPane.QUESTION_MESSAGE,
																			null,
																			deviceArray,
																			deviceArray[0]);
															showMessageDialog(Constants.PUSHING_APP);
															EmulatorWorker task = new EmulatorWorker(
																	"pushAppOntoDevice",
																	"",
																	selection,
																	appPath,
																	BaseViewPanel.this);
															task.addPropertyChangeListener(new PropertyChangeListener() {
																public void propertyChange(
																		PropertyChangeEvent evt) {
																	if ("progress"
																			.equals(evt
																					.getPropertyName())) {
																		showMessageDialog((String) evt
																				.getNewValue());
																	}
																}
															});
															task.execute();
														}
													} else {
														showMessageDialog(Constants.NO_ANDROID_DEVICES_AVAILABLE);
													}
												}
											} catch (NullPointerException e1) {
												e1.getMessage();
											}
										}
									}
								} else {
									showMessageDialog(Constants.SELECT_AN_APP);
								}
							} catch (FileNotFoundException e1) {
								e1.getMessage();
							} catch (IOException e1) {
								e1.getMessage();
							} catch (CorruptConfigException e1) {
								e1.getMessage();
							}
						}
					});
		}
		return homeAppsPushAppButton;
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private void setDisplayWebServiceStarted() {
		homeAppsStartWebServiceButton.setText("Stop Web Service");
		homeAppsStartWebServiceButton.setForeground(Color.red);
	}

	private void setDisplayWebServiceStopped() {
		homeAppsStartWebServiceButton.setText("Start Web Service");
		homeAppsStartWebServiceButton.setForeground(Color.black);
	}
}
