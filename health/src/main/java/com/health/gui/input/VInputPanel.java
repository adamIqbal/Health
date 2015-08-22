package com.health.gui.input;

import com.health.gui.VidneyPanel;
import com.health.gui.input.xmlwizard.XmlWizard;

/**
 * Represents the panel where all files are loaded into the program.
 * 
 * @author Bjorn van der Laan
 *
 */
public class VInputPanel extends VidneyPanel {
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 2343407366589926543L;
	private FileSelectionPanel fileSelectionPanel;

	/**
	 * Constructor.
	 */
	public VInputPanel() {
		super();

		fileSelectionPanel = new FileSelectionPanel();
		this.setLeft(fileSelectionPanel);

		XmlWizard filePanel = new XmlWizard();
		this.setRight(filePanel);
	}

	public FileSelectionPanel getFileSelectionPanel() {
		return fileSelectionPanel;
	}
}
