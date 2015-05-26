package com.health.gui.xmlwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFrame;

public class XmlWizard extends JFrame implements ActionListener {
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
		filePanel.addActionListenerToNewFileButton(this);
		filePanel.addActionListenerToSelectFileButton(this);
		editPanel.addActionListenerToContinueButton(this);
			
		this.setTitle("XML Editor");
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if(source.equals(filePanel.getNewFileButton())) {
			this.getContentPane().remove(filePanel);
			this.setContentPane(editPanel);
			this.repaint();
			this.revalidate();
		}
		else if(source.equals(filePanel.getSelectFileButton())) {
			if(filePanel.getSelectedFile() != null) {				
				editPanel.setValues(filePanel.getSelectedFile());
				this.getContentPane().remove(filePanel);
				this.setContentPane(editPanel);
				this.repaint();
				this.revalidate();	
			}
		}
		else if(source.equals(editPanel.getContinueButton())) {
			XmlConfigObject config = editPanel.getValues();
			savePanel.setValues(config);
			this.getContentPane().remove(editPanel);
			this.setContentPane(savePanel);
			this.repaint();
			this.revalidate();	
		}
		else {
			System.out.println(source.toString());
		}
	}
}