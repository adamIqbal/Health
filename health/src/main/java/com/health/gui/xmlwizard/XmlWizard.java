package com.health.gui.xmlwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The frame of the XML Wizard. Contains all panels and controls transition
 * between them.
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlWizard extends JFrame {
	private XmlFilePanel filePanel;
	private XmlEditPanel editPanel;
	private XmlSavePanel savePanel;

	/**
	 * Constructs a XmlWizard containing the wizard panels
	 * 
	 * @param path
	 *            a Path object referring to the Config XML folder
	 */
	public XmlWizard(Path path) {
		super();
		this.setSize(500, 500);

		filePanel = new XmlFilePanel(path);
		editPanel = new XmlEditPanel();
		savePanel = new XmlSavePanel();

		this.getContentPane().add(filePanel);

		XmlWizardListener wizardListener = new XmlWizardListener(this);
		wizardListener.attachListenerToButtons();

		this.setTitle("XML Editor");
		this.setVisible(true);
	}

	/**
	 * Changes the current visible panel.
	 * 
	 * @param next
	 *            the JPanel that will be made visible
	 */
	protected void changePanel(JPanel next) {
		this.getContentPane().removeAll();
		this.setContentPane(next);
		this.repaint();
		this.revalidate();
	}

	/**
	 * Returns the XmlFilePanel. This method is used by the private class
	 * implementing ActionListener
	 * 
	 * @return the XmlFilePanel
	 */
	protected XmlFilePanel getFilePanel() {
		return filePanel;
	}

	/**
	 * Returns the XmlEditPanel. This method is used by the private class
	 * implementing ActionListener
	 * 
	 * @return the XmlEditPanel
	 */
	protected XmlEditPanel getEditPanel() {
		return editPanel;
	}

	/**
	 * Returns the XmlSavePanel. This method is used by the private class
	 * implementing ActionListener
	 * 
	 * @return the XmlSavePanel
	 */
	protected XmlSavePanel getSavePanel() {
		return savePanel;
	}

	/**
	 * XmlWizardListener handles all transitions between panels of the XmlWizard
	 * It implements the ActionListener interface
	 * 
	 * @author Bjorn van der Laan
	 *
	 */
	private class XmlWizardListener implements ActionListener {
		private XmlWizard wizardFrame;
		private XmlFilePanel filePanel;
		private XmlEditPanel editPanel;
		private XmlSavePanel savePanel;

		public XmlWizardListener(XmlWizard xw) {
			super();
			wizardFrame = xw;
			filePanel = wizardFrame.getFilePanel();
			editPanel = wizardFrame.getEditPanel();
			savePanel = wizardFrame.getSavePanel();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source.equals(filePanel.getNewFileButton())) {
				wizardFrame.changePanel(editPanel);
			} else if (source.equals(filePanel.getSelectFileButton())) {
				if (filePanel.getSelectedFile() != null) {
					editPanel.setValues(filePanel.getSelectedFile());
					wizardFrame.changePanel(editPanel);
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"You have not selected a file yet!", "Error!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (source.equals(editPanel.getContinueButton())) {
				XmlConfigObject config = editPanel.getValues();
				savePanel.setValues(config);
				wizardFrame.changePanel(savePanel);
			}
		}

		/**
		 * Attaches the XmlWizardListener to the buttons in the different
		 * panels.
		 */
		public void attachListenerToButtons() {
			filePanel.addActionListenerToNewFileButton(this);
			filePanel.addActionListenerToSelectFileButton(this);
			editPanel.addActionListenerToContinueButton(this);
		}
	}
}