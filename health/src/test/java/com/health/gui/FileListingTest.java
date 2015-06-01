package com.health.gui;

import static org.junit.Assert.*;

import javax.swing.JTextField;

import org.junit.Test;
import org.uispec4j.Panel;

public class FileListingTest {

	@Test
	public void makeInitialFrame(){
		Panel fileListing = new Panel(new FileListing());
		JTextField header = fileListing.findSwingComponent(JTextField.class, "File");
		
	}
	

}
