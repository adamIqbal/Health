package com.health.gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * Sidebar displaying several hints about the script.
 * @author Bjorn van der Laan
 *
 */
public class ScriptPanelSidebar extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1458489943027565686L;

    public ScriptPanelSidebar() {
        super();
        this.setLayout(new CardLayout());
    }
}
