package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Screen where the user can either select a xml file to edit, or create a new xml file
 * @author Bjorn
 *
 */
class XmlFilePanel extends JPanel {	
	private JButton newFileButton;
	private JButton selectFileButton;
	private FileList fileList;

	public XmlFilePanel(String path) {
		super();
		this.setLayout(new BorderLayout());
		
		//add list model
		DefaultListModel<Path> listModel = new DefaultListModel<Path>();
		fileList = new FileList(path, listModel);
		this.add(fileList, BorderLayout.CENTER);
		
		//add buttons
		JPanel buttonPanel = new JPanel();
		newFileButton = new JButton("Create a new file");
		selectFileButton = new JButton("Edit selected file");
		buttonPanel.add(newFileButton);
		buttonPanel.add(selectFileButton);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public JButton getNewFileButton() {
		return newFileButton;
	}

	public JButton getSelectFileButton() {
		return selectFileButton;
	}
	
	public void addActionListenerToNewFileButton(ActionListener al) {
		newFileButton.addActionListener(al);
	}
	
	public void addActionListenerToSelectFileButton(ActionListener al) {
		selectFileButton.addActionListener(al);
	}
	
	public Path getSelectedFile() {
		return this.fileList.getSelectedValue();
	}
}

class FileList extends JList<Path> {
	public FileList(String path, DefaultListModel<Path> listModel) {
		super(listModel);
		this.setPreferredSize(new Dimension(200,300));
		this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
		
		Path dir = Paths.get(path);

		try {
			DirectoryStream<Path> stream;
			stream = Files.newDirectoryStream(dir);
			Iterator<Path> iterator = stream.iterator();
			
			while(iterator.hasNext()) {
				listModel.addElement(iterator.next());
			}
		} catch (IOException e) {
			//Directory not found, add no elements
		}		
	}
}