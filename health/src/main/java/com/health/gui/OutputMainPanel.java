package com.health.gui;

import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.health.Table;

public class OutputMainPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5652640933659529127L;
    private HashMap<String, Object> map;
    
    public OutputMainPanel() {
        super();
    }
    
    public void setData(HashMap<String, Object> data) {
        this.map = data;
        JTabbedPane pane = new JTabbedPane();
        System.out.println(data.keySet().toString());
        for(String key : data.keySet()) {
            if(data.get(key) instanceof Table) {
                Table table = (Table) data.get(key);
                pane.add("Tab", table.toJTable());
            }
        }
        this.removeAll();
        this.add(pane);
        
        this.repaint();
        this.revalidate();
    }
}
