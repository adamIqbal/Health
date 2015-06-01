package com.health.gui.xmlwizard.starteditsubpanels;

import javax.swing.JLabel;

import com.health.FileType;

/**
 * Represents the panel to specify parameters of the config XML describing an
 * XLS file that are not column-related.
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlXlsEditPanel extends XmlStartEditSubPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -4777267173840186038L;

    /**
     * Constructs an XmlXlsEditPanel.
     */
    public XmlXlsEditPanel() {
        super();
        this.setType(FileType.XLS);

        JLabel placeholder = new JLabel("XLS!");
        this.add(placeholder);
    }
}
