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
public abstract class VidneyPanel extends JSplitPane {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1804783858852989085L;
    private JPanel mainPanel;
    private JPanel sidePanel;
    private static final int PADDING = 20;

    /**
     * Constructor.
     */
    public VidneyPanel() {
        super();

        this.setDefaultPanels();
        this.setContinuousLayout(true);
        this.setDividerSize(0);
        final double dividerLocation = 0.7;
        this.setResizeWeight(dividerLocation);
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

    /**
     * Sets layout options of the panel to create a consistent look.
     * @param panel the panel to be inserted
     * @return the panel, styled to fit in the application
     */
    private JPanel preparePanel(final JPanel panel) {
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        panel.setBackground(UserInterface.GUI_COLOR);
        return panel;
    }

    /**
     * Sets the left (side) panel of this panel.
     * @param rawPanel
     *            the panel to set
     */
    protected final void setLeft(final JPanel rawPanel) {
        JPanel panel = preparePanel(rawPanel);
        this.setLeftComponent(panel);
    }

    /**
     * Sets the right (main) panel of this panel.
     * @param rawPanel
     *            the panel to set
     */
    protected final void setRight(final JPanel rawPanel) {
        JPanel panel = preparePanel(rawPanel);
        this.setRightComponent(panel);
    }
}
