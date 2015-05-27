package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

import com.health.gui.FileListing;

public class XmlSavePanel extends JPanel {
	private XmlConfigObject xml;
	private JTextArea preview;
	private JPanel buttonPanel;
	
	public XmlSavePanel() {
		super();
		this.setLayout(new BorderLayout());
		
		preview = new JTextArea();
		preview.setEditable(false);
		preview.setLineWrap(true);
		preview.setWrapStyleWord(true);
		preview.setPreferredSize(getPreferredSize());
		this.add(preview, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		JButton saveAsButton = new JButton("Save as..");
		saveAsButton.addActionListener(new XmlSaveAsListener());
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
	
	private boolean save() {
		//Get the String to write
		String xmlString = xml.toXMLString();
		//Get path to write to
		String path = xml.path.toString();
		
		//TODO write to file
		
		//return if write was successful
		return false;
		
	}
	
	private void saveAs() throws IOException {
		//Get the String to write
		String xmlString = xml.toXMLString();
		
		//Save to xml file
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify where to save");
		
		File target = null;
		int selection = fileChooser.showSaveDialog(this);
		if(selection == JFileChooser.APPROVE_OPTION) {
			target = fileChooser.getSelectedFile();
		}
		
		if(target != null) {
			FileUtils.writeStringToFile(target, xmlString);
		}
	}
	
	private class XmlSaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				saveAs();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(new JFrame(), "'Save as..' operation has failed. Please try again.", "Error!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	 
}
