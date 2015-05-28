package com.health.gui.xmlwizard.editsubpanels;

import javax.swing.JPanel;

import com.health.FileType;

/**
 * Abstract class for panels for different filetypes
 * @author Bjorn van der Laan
 *
 */
public abstract class XmlStartEditSubPanel extends JPanel {
	public FileType type;
	public String[] getValues() { return null; };
	public void setValues(String[] values) {};
}
