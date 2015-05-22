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
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

public class XmlWizard extends JFrame {
	public XmlWizard(String path) {
		super();
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		
		try {
			this.add(new XmlPanel(path), BorderLayout.CENTER);
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
		
		DefaultListModel<Path> listModel = new DefaultListModel<Path>();
		this.add(new FileList(path, listModel));
		
	}
}

class FileList extends JList<Path> {
	public FileList(String path, DefaultListModel<Path> listModel) throws IOException {
		super(listModel);
		this.setPreferredSize(new Dimension(200,300));
		this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
		
		Path dir = Paths.get(path);
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		Iterator<Path> iterator = stream.iterator();
		
		while(iterator.hasNext()) {
			listModel.addElement(iterator.next());
		}
		for(int i = 0; i < 10; i++) {
			listModel.addElement(Paths.get("path", "to", "files"));
		}
		
	}
}