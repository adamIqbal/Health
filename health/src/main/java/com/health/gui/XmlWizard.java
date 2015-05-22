package com.health.gui;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.*;

public class XmlWizard extends JFrame {
	public XmlWizard(String path) {
		super();
		this.setSize(500, 500);
		
		try {
			this.add(new XmlPanel(path));
		} catch (IOException e) {
			//Some file not found warning
			e.printStackTrace();
		}
		this.setTitle("XML Editor");
		this.setVisible(true);
	}
	
}

class XmlPanel extends JPanel {
	public XmlPanel(String path) throws IOException {
		super();
		DefaultListModel listModel = new DefaultListModel();
		this.add(new FileList(path, listModel));
		
	}
}

class FileList extends JList {
	public FileList(String path, DefaultListModel listModel) throws IOException {
		super(listModel);
		
		Path dir = Paths.get(path);
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		Iterator<Path> iterator = stream.iterator();
		
		while(iterator.hasNext()) {
			listModel.addElement(iterator.next().toString());
		}
		
	}
}