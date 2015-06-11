package com.health.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

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

        // Should be 618 for perfect GoldenRatio between panels
        final int dividerLocation = 650;

        this.setDefaultPanels();

        this.setContinuousLayout(true);
        this.setDividerLocation(dividerLocation);
        this.setDividerSize(0);
        setEnabled(false);
    }

    private void setDefaultPanels() {
        mainPanel = new JPanel();
        mainPanel.add(new JLabel("Mainpanel"));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        sidePanel = new JPanel();
        sidePanel.add(new JLabel("Sidebar"));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        this.setLeft(mainPanel);
        this.setRight(sidePanel);
    }

    protected void setLeft(final JPanel panel) {
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(GUImain.GUI_COLOR);
        this.setLeftComponent(panel);
    }

    protected void setRight(final JPanel panel) {
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(GUImain.GUI_COLOR);
        this.setRightComponent(panel);
    }

}
