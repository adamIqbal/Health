package com.health.gui.xmlwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class XmlWizard extends JFrame {
	private XmlFilePanel filePanel;
	private XmlEditPanel editPanel;

	private XmlSavePanel savePanel;

	public XmlWizard(Path path) {
		super();
		this.setSize(500, 500);

		filePanel = new XmlFilePanel(path);
		editPanel = new XmlEditPanel();
		savePanel = new XmlSavePanel();

		this.getContentPane().add(filePanel);
		XmlWizardListener wizardListener = new XmlWizardListener(this,
				filePanel, editPanel, savePanel);
		filePanel.addActionListenerToNewFileButton(wizardListener);
		filePanel.addActionListenerToSelectFileButton(wizardListener);
		editPanel.addActionListenerToContinueButton(wizardListener);

		this.setTitle("XML Editor");
		this.setVisible(true);
	}

	public void changePanel(JPanel next) {
		this.getContentPane().removeAll();
		this.setContentPane(next);
		this.repaint();
		this.revalidate();
	}

	private class XmlWizardListener implements ActionListener {
		private XmlWizard wizardFrame;
		private XmlFilePanel filePanel;
		private XmlEditPanel editPanel;
		private XmlSavePanel savePanel;

		public XmlWizardListener(XmlWizard xw, XmlFilePanel fp,
				XmlEditPanel ep, XmlSavePanel sp) {
			super();
			wizardFrame = xw;
			filePanel = fp;
			editPanel = ep;
			savePanel = sp;
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
				}
			} else if (source.equals(editPanel.getContinueButton())) {
				XmlConfigObject config = editPanel.getValues();
				savePanel.setValues(config);
				wizardFrame.changePanel(savePanel);
			}
		}
	}
}