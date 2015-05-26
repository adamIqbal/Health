package com.health.gui.xmlwizard;

import java.awt.BorderLayout;

import javax.swing.*;

public class XmlSavePanel extends JPanel {
	private XmlConfigObject xml;
	private JTextArea preview;
	private JPanel buttonPanel;
	
	public XmlSavePanel() {
		super();
		this.setLayout(new BorderLayout());
		
		preview = new JTextArea();
		preview.setLineWrap(true);
		preview.setWrapStyleWord(true);
		preview.setPreferredSize(getPreferredSize());
		this.add(preview, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		JButton saveAsButton = new JButton("Save as..");
		buttonPanel.add(saveAsButton);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void setValues(XmlConfigObject xml_param) {
		this.xml = xml_param;
		preview.setText(xml.toXMLString());
		
		//Add save button if editing an existing file
		if(xml.path != null) {
			JButton saveButton = new JButton("Save");
			buttonPanel.add(saveButton);
		}
	}
	 
}
