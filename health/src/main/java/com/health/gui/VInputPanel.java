package com.health.gui;

import javax.swing.JPanel;

import com.health.gui.fileSelection.FileSelectionPanel;

/**
 * Represents the panel where all files are loaded into the program.
 * @author Bjorn van der Laan
 *
 */
public class VInputPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 2343407366589926543L;

    /**
     * Constructor.
     */
    public VInputPanel() {
        super();

        JPanel fileSelectionPanel = new FileSelectionPanel();
        this.setLeftComponent(fileSelectionPanel);
    }
}
