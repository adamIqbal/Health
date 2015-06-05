package com.health.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class VInputPanel extends VidneyPanel {    
    public VInputPanel() {
        super();
    }
    
    public void init() {
        JPanel fileSelectionPanel = new FileSelectionPanel();
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("mainbar"));
        this.setMainPanel(fileSelectionPanel);
        
        JPanel sidePanel = new JPanel();
        sidePanel.add(new JLabel("sidebar"));
        this.setSidePanel(sidePanel);
    }
}
