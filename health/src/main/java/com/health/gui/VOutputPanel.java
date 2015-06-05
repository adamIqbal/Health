package com.health.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Represents the panel where the script is typed.
 * @author Bjorn van der Laan
 *
 */
public class VOutputPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5303011708825739028L;
    private static JTextArea outputArea;

    /**
     * Constructor.
     */
    public VOutputPanel() {
        super();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        outputArea = new JTextArea(2, 1);

        JScrollPane scrollforOutputArea = new JScrollPane(outputArea);
        mainPanel.add(scrollforOutputArea, BorderLayout.CENTER);

        this.setLeftComponent(mainPanel);
    }

    /**
     * set the text of the display area.
     *
     * @param result
     *            the result of the analysis
     */
    public static void displayData(final String result) {
        outputArea.setText(result);
        outputArea.revalidate();
        outputArea.repaint();
    }
}
