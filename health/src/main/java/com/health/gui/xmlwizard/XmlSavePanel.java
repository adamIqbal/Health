package com.health.gui.xmlwizard;

import java.awt.BorderLayout;

import javax.swing.*;

public class XmlSavePanel extends JPanel {
	private XmlConfigObject xml;
	private JTextArea preview;
	private JTextField fileNameField;
	
	public XmlSavePanel() {
		super();
		this.setLayout(new BorderLayout());
		
		preview = new JTextArea();
		preview.setLineWrap(true);
		preview.setWrapStyleWord(true);
		preview.setPreferredSize(getPreferredSize());
		this.add(preview, BorderLayout.CENTER);
		
		fileNameField = new JTextField();
		this.add(fileNameField, BorderLayout.SOUTH);
	}
	
	public void setValues(XmlConfigObject xml_param) {
		this.xml = xml_param;
		preview.setText(xml.toXMLString());
		fileNameField.setText(xml.path.toAbsolutePath().toString());
	}
	 
}
