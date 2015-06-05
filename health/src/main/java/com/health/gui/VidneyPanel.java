package com.health.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class VidneyPanel extends JPanel {
    private JPanel mainPanel;
    private JPanel sidePanel;
    
    public VidneyPanel() {
        super();
        
        mainPanel = new JPanel();
        mainPanel.add(new JLabel("Mainpanel"));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        
        sidePanel = new JPanel();
        sidePanel.add(new JLabel("Sidebar"));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        
        init();
        
        buildLayout();
    }
    
    abstract void init();
    
    public void buildLayout() {
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints panelConstraints = new GridBagConstraints();

        panelConstraints.insets = new Insets(3,3,3,3);
        panelConstraints.fill = GridBagConstraints.BOTH;
        panelConstraints.weightx = 1.0;
        panelConstraints.weighty = 1.0;
        
        panelConstraints.gridheight = 1;
        
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panelConstraints.gridwidth = 2;
        this.add(mainPanel, panelConstraints);
        
        panelConstraints.gridx = 2;
        panelConstraints.gridy = 0;
        panelConstraints.gridwidth = 1;
        this.add(sidePanel, panelConstraints);
        
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
    
}
