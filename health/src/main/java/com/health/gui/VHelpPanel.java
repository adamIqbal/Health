package com.health.gui;

import java.awt.CardLayout;

import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Represents a panel where users can find documentation of the application.
 * @author Bjorn van der Laan
 *
 */
public class VHelpPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -8767468584587913052L;
    private JList<String> topicList;
    private JPanel cardPanel;
    
    /**
     * Constructor.
     */
    public VHelpPanel() {
        super();
        
        topicList = new JList<String>();
        JPanel topics = new JPanel();
        topics.add(topicList);
        
        cardPanel = new JPanel(new CardLayout());
        
        this.setLeft(cardPanel);
        this.setRight(topics);
    }
    
    private void addCard(String name, String text) {
        //text should be loaded from textfile in future.
        
    }
}
