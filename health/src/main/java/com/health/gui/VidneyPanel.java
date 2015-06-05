package com.health.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

abstract class VidneyPanel extends JSplitPane {
    private JPanel mainPanel;
    private JPanel sidePanel;
    
    public VidneyPanel() {
        super();
        
        this.setDefaultPanels();
        
        this.setContinuousLayout(true);
        this.setDividerLocation(750);
        this.setDividerSize(0);
        setEnabled(false);
        
    }    

    protected JPanel getMainPanel() {
        return mainPanel;
    }

    protected void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    protected JPanel getSidePanel() {
        return sidePanel;
    }

    protected void setSidePanel(JPanel sidePanel) {
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
