package com.health.gui.output;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.health.gui.VButton;
import com.health.gui.VidneyPanel;

/**
 * Represents the panel where the script is typed.
 * @author Bjorn van der Laan and Daan Vermunt
 *
 */
public class VOutputPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5303011708825739028L;
    private VButton prevButton;
    private OutputPanelSidebar sidebar;
    private OutputMainPanel mainPanel;


    /**
     * Constructor.
     */
    public VOutputPanel() {
        super();

        mainPanel = new OutputMainPanel();
        this.setLeft(mainPanel);

        sidebar = new OutputPanelSidebar();

        this.setRight(sidebar);

        prevButton = new VButton("Return");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(prevButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Get previous button.
     * @return	previous button
     */
    public final VButton getPrevButton() {
    	return prevButton;
    }

    /**
     * Get sidebar.
     * @return	sidebar
     */
    public final OutputPanelSidebar getSidebar() {
    	return sidebar;
    }

    /**
     * Get main panel.
     * @return	main panel
     */
    public final OutputMainPanel getMainPanel() {
    	return mainPanel;
    }
}
