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

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.owasp.goatdroid.gui.Constants;
import org.owasp.goatdroid.gui.config.Config;
import org.owasp.goatdroid.gui.emulator.Emulator;
import org.owasp.goatdroid.gui.emulator.EmulatorWorker;
import org.owasp.goatdroid.gui.exception.CorruptConfigException;
import org.owasp.goatdroid.gui.view.panel.AppsPanel;
import org.owasp.goatdroid.gui.view.panel.LessonPanel;
import org.owasp.goatdroid.gui.view.panel.Top10Panel;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar homeMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu configurationMenu = null;
	private JMenu aboutMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem configurationEditMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenu toolsMenu = null;
	private JMenuItem toolsSendLocationMenuItem = null;
	private JMenu viewMenu;
	private JCheckBoxMenuItem viewByAppsViewMenuCheckItem;
	private JCheckBoxMenuItem viewByLessonViewMenuCheckItem;
	private JCheckBoxMenuItem viewByTop10ViewMenuCheckItem;
	private JMenuItem toolsSendSMSMenuItem;
	private JMenuItem toolsCallDeviceMenuItem;
	private JMenu sendToDeviceMenu;
	private JMenu utilitiesMenu;
	private JMenuItem databaseBrowserMenuItem;
	private JMenu launchMenu;
	private JMenuItem launchAndroidManager;

	public HomeFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(1000, 750);
		this.setPreferredSize(new Dimension(400, 560));
		this.setResizable(true);
		this.setJMenuBar(getHomeMenuBar());
		AppsPanel appsPanel = new AppsPanel();
		this.setContentPane(appsPanel);
		this.setTitle("OWASP GoatDroid");
	}

	private JMenuBar getHomeMenuBar() {
		if (homeMenuBar == null) {
			homeMenuBar = new JMenuBar();
			homeMenuBar.add(getFileMenu());
			homeMenuBar.add(getConfigurationMenu());
			homeMenuBar.add(getViewMenu());
			homeMenuBar.add(getToolsMenu());
			homeMenuBar.add(getAboutMenu());
		}
		return homeMenuBar;
	}

	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	private JMenu getConfigurationMenu() {
		if (configurationMenu == null) {
			configurationMenu = new JMenu();
			configurationMenu.setText("Configure");
			configurationMenu.add(getConfigurationEditMenuItem());
		}
		return configurationMenu;
	}

	private JMenu getAboutMenu() {
		if (aboutMenu == null) {
			aboutMenu = new JMenu();
			aboutMenu.setText("About GoatDroid");
			aboutMenu.add(getAboutMenuItem());
		}
		return aboutMenu;
	}

	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	private JMenuItem getConfigurationEditMenuItem() {
		if (configurationEditMenuItem == null) {
			configurationEditMenuItem = new JMenuItem();
			configurationEditMenuItem.setText("Edit Configuration");
			configurationEditMenuItem
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							ConfigureEditFrame editFrame;
							try {
								editFrame = new ConfigureEditFrame();
								editFrame.setVisible(true);
							} catch (FileNotFoundException e1) {
								showMessageDialog(Constants.CORRUPT_CONFIG);
							} catch (IOException e1) {
								showMessageDialog(Constants.CORRUPT_CONFIG);
							} catch (CorruptConfigException e1) {
								showMessageDialog(Constants.CORRUPT_CONFIG);
							}
						}
					});
		}
		return configurationEditMenuItem;
	}

	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					AboutFrame aboutFrame = new AboutFrame();
					aboutFrame.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	private JMenu getToolsMenu() {
		if (toolsMenu == null) {
			toolsMenu = new JMenu();
			toolsMenu.setText("Tools");
			toolsMenu.add(getUtilitiesMenu());
			toolsMenu.add(getSendToDeviceMenu());
			toolsMenu.add(getLaunchMenu());
		}
		return toolsMenu;
	}

	private JMenuItem getToolsSendLocationMenuItem() {
		if (toolsSendLocationMenuItem == null) {
			toolsSendLocationMenuItem = new JMenuItem();
			toolsSendLocationMenuItem.setText("Send Location");
			toolsSendLocationMenuItem
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseReleased(java.awt.event.MouseEvent e) {
							SendLocationFrame sendLocation = new SendLocationFrame();
							sendLocation.setVisible(true);
						}
					});
		}
		return toolsSendLocationMenuItem;
	}

	private JMenu getViewMenu() {
		if (viewMenu == null) {
			viewMenu = new JMenu("View");
			viewMenu.add(getViewByAppsViewMenuCheckItem());
			viewMenu.add(getViewByLessonViewMenuCheckItem());
			viewMenu.add(getViewByTop10ViewMenuCheckItem());
		}
		return viewMenu;
	}

	private JCheckBoxMenuItem getViewByAppsViewMenuCheckItem() {
		if (viewByAppsViewMenuCheckItem == null) {
			viewByAppsViewMenuCheckItem = new JCheckBoxMenuItem("App View");
			viewByAppsViewMenuCheckItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					viewByAppsViewMenuCheckItem.setSelected(true);
					viewByLessonViewMenuCheckItem.setSelected(false);
					viewByTop10ViewMenuCheckItem.setSelected(false);
					setFrameContentPane(new AppsPanel());
					validateFrame();
				}
			});
			viewByAppsViewMenuCheckItem.setSelected(true);
		}
		return viewByAppsViewMenuCheckItem;
	}

	private JCheckBoxMenuItem getViewByLessonViewMenuCheckItem() {
		if (viewByLessonViewMenuCheckItem == null) {
			viewByLessonViewMenuCheckItem = new JCheckBoxMenuItem("Lesson View");
			viewByLessonViewMenuCheckItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					viewByAppsViewMenuCheckItem.setSelected(false);
					viewByLessonViewMenuCheckItem.setSelected(true);
					viewByTop10ViewMenuCheckItem.setSelected(false);
					setFrameContentPane(new LessonPanel());
					validateFrame();
				}
			});
		}
		return viewByLessonViewMenuCheckItem;
	}

	private JCheckBoxMenuItem getViewByTop10ViewMenuCheckItem() {
		if (viewByTop10ViewMenuCheckItem == null) {
			viewByTop10ViewMenuCheckItem = new JCheckBoxMenuItem("Top 10 View");
			viewByTop10ViewMenuCheckItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					viewByAppsViewMenuCheckItem.setSelected(false);
					viewByLessonViewMenuCheckItem.setSelected(false);
					viewByTop10ViewMenuCheckItem.setSelected(true);
					setFrameContentPane(new Top10Panel());
					validateFrame();
				}
			});
		}
		return viewByTop10ViewMenuCheckItem;
	}

	private JMenuItem getToolsSendSMSMenuItem() {
		if (toolsSendSMSMenuItem == null) {
			toolsSendSMSMenuItem = new JMenuItem("Send SMS");
			toolsSendSMSMenuItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					SendSMSFrame sendSMS = new SendSMSFrame();
					sendSMS.setVisible(true);
				}
			});
		}
		return toolsSendSMSMenuItem;
	}

	private JMenuItem getToolsCallDeviceMenuItem() {
		if (toolsCallDeviceMenuItem == null) {
			toolsCallDeviceMenuItem = new JMenuItem("Phone Call");
			toolsCallDeviceMenuItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					CallDeviceFrame callDevice = new CallDeviceFrame();
					callDevice.setVisible(true);
				}
			});
		}
		return toolsCallDeviceMenuItem;
	}

	private void validateFrame() {
		this.validate();
	}

	private void setFrameContentPane(JPanel panel) {
		this.setContentPane(panel);
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private JMenu getSendToDeviceMenu() {
		if (sendToDeviceMenu == null) {
			sendToDeviceMenu = new JMenu("Send To Device");
			sendToDeviceMenu.add(getToolsSendLocationMenuItem());
			sendToDeviceMenu.add(getToolsSendSMSMenuItem());
			sendToDeviceMenu.add(getToolsCallDeviceMenuItem());
		}
		return sendToDeviceMenu;
	}

	private JMenu getUtilitiesMenu() {
		if (utilitiesMenu == null) {
			utilitiesMenu = new JMenu("Utilities");
			utilitiesMenu.add(getDatabaseBrowserMenuItem());
		}
		return utilitiesMenu;
	}

	private JMenuItem getDatabaseBrowserMenuItem() {
		if (databaseBrowserMenuItem == null) {
			databaseBrowserMenuItem = new JMenuItem("Database Browser");
			databaseBrowserMenuItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					DatabaseBrowserFrame databaseBrowserFrame = new DatabaseBrowserFrame();
					databaseBrowserFrame.setVisible(true);
				}
			});
		}
		return databaseBrowserMenuItem;
	}

	private JMenu getLaunchMenu() {
		if (launchMenu == null) {
			launchMenu = new JMenu("Launch");
			launchMenu.add(getLaunchAndroidManager());
		}
		return launchMenu;
	}

	private JMenuItem getLaunchAndroidManager() {
		if (launchAndroidManager == null) {
			launchAndroidManager = new JMenuItem("Android SDK Manager");
			launchAndroidManager.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					try {
						if (!Config.getSDKPath().equals("")) {
							if (Emulator.doesAndroidManagerToolExist()) {
								EmulatorWorker task = new EmulatorWorker(
										"startAndroidSDKManager", "", "", "",
										HomeFrame.this);
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
								showMessageDialog(Constants.INVALID_SDK_PATH);
							}
						} else {
							showMessageDialog(Constants.SPECIFY_SDK_LOCATION);
						}
					} catch (CorruptConfigException e) {
						showMessageDialog(Constants.CORRUPT_CONFIG);

					} catch (FileNotFoundException e) {
						showMessageDialog(Constants.INVALID_SDK_PATH);

					} catch (IOException e) {
						showMessageDialog(Constants.UNEXPECTED_ERROR);

					}
				}
			});
		}
		return launchAndroidManager;
	}
}
