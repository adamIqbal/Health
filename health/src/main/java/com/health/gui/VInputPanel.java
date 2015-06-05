package com.health.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class VInputPanel extends VidneyPanel {    
    public VInputPanel() {
        super();
        
        JPanel fileSelectionPanel = new FileSelectionPanel();
        this.setLeftComponent(fileSelectionPanel);
    }
}
