package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.health.Table;

public class OutputMainPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5652640933659529127L;
    private HashMap<String, Object> map;

    public OutputMainPanel() {
        super();
        this.setLayout(new BorderLayout());

        JTabbedPane pane = new JTabbedPane();
        pane.setBackground(Color.WHITE);
        this.add(pane, BorderLayout.CENTER);
    }

    public void setData(HashMap<String, Object> data) {
        this.map = data;
        JTabbedPane pane = new JTabbedPane();
        for (String key : data.keySet()) {
            if (data.get(key) instanceof Table) {
                Table table = (Table) data.get(key);
                JTable jtable = table.toJTable();
                jtable.setEnabled(false);
                JScrollPane scroll = new JScrollPane(jtable);
                pane.add("Tab", scroll);
            }
        }
        this.removeAll();
        this.add(pane);

        this.repaint();
        this.revalidate();
    }
}
