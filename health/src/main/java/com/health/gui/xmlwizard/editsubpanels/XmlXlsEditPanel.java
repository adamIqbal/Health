package com.health.gui.xmlwizard.editsubpanels;

import javax.swing.JLabel;

import com.health.FileType;

/**
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlXlsEditPanel extends XmlStartEditSubPanel {
	public XmlXlsEditPanel() {
		super();
		type = FileType.XLS;

		JLabel placeholder = new JLabel("XLS!");
		this.add(placeholder);
	}
}
