package com.health.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Class creates and fills the input panel under the input tab.
 *
 * @author Daan
 */
public class InputPanel extends JPanel {
	/**
	 * Method to devide the inputpanel in two tabs
	 * for script and fileselection.
	 */
	public InputPanel() {
		this.setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel scriptPanel = new ScriptPanel();
		JPanel fileSelectionPanel = new FileSelectionPanel();

		tabbedPane.addTab("File Selection", fileSelectionPanel);
		tabbedPane.addTab("Script", scriptPanel);

		this.add(tabbedPane, BorderLayout.CENTER);

	}

}
