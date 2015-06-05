package com.health.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Abstract class that is extended by all main panels of the GUI. Defines the
 * two panel structure.
 * @author Bjorn van der Laan
 *
 */
abstract class VidneyPanel extends JSplitPane {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1804783858852989085L;
    private JPanel mainPanel;
    private JPanel sidePanel;

    /**
     * Constructor.
     */
    public VidneyPanel() {
        super();

        final int dividerLocation = 750;

        this.setDefaultPanels();

        this.setContinuousLayout(true);
        this.setDividerLocation(dividerLocation);
        this.setDividerSize(0);
        setEnabled(false);
    }

    protected JPanel getMainPanel() {
        return mainPanel;
    }

    protected void setMainPanel(final JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    protected JPanel getSidePanel() {
        return sidePanel;
    }

    protected void setSidePanel(final JPanel sidePanel) {
        this.sidePanel = sidePanel;
    }

    private void setDefaultPanels() {
        mainPanel = new JPanel();
        mainPanel.add(new JLabel("Mainpanel"));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        sidePanel = new JPanel();
        sidePanel.add(new JLabel("Sidebar"));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        this.setLeftComponent(mainPanel);
        this.setRightComponent(sidePanel);
    }

}
