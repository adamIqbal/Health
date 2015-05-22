package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class XmlWizard extends JFrame {
	public XmlWizard(String path) {
		super();
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		
		this.add(new XmlFilePanel(path), BorderLayout.WEST);
		
		this.setTitle("XML Editor");
		this.setVisible(true);
	}
	
}

class XmlFilePanel extends JPanel {	
	public XmlFilePanel(String path) {
		super();
		DefaultListModel<Path> listModel = new DefaultListModel<Path>();
		this.add(new FileList(path, listModel));
	}
}

class XmlEditPanel extends JPanel {
	public XmlEditPanel() {
		super();
	}
}

class XmlButtonPanel extends JPanel {
	public XmlButtonPanel() {
		super();
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
				listModel.addElement(iterator.next().getFileName());
			}
		} catch (IOException e) {
			//Directory not found, add no elements
		}
		
		//Just dummy Path objects
		for(int i = 0; i < 10; i++) {
			listModel.addElement(Paths.get("path", "to", "files", Integer.toString(i)));
		}
		
	}
}