package com.health.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VOutputPanel extends VidneyPanel {
    private static JTextArea outputArea;

    /**
     * Method to display all outputData.
     */
    public VOutputPanel() {
        super();
    }
    
    public void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        outputArea = new JTextArea(2, 1);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 30));

        JScrollPane scrollforOutputArea = new JScrollPane(outputArea);
        mainPanel.add(scrollforOutputArea, BorderLayout.CENTER);
        
        this.setMainPanel(mainPanel);
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
