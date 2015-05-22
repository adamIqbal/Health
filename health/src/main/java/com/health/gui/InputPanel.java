package com.health.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
		JButton xmlwiz = new JButton("XmlWizard");
		xmlwiz.addActionListener(new XmlWizardListener());
		this.add(xmlwiz, BorderLayout.SOUTH);

	}

}

class XmlWizardListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		XmlWizard xml = new XmlWizard("data/configXmls/");
	}
	
}
