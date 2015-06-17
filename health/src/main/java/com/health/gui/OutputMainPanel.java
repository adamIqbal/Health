package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.health.Table;

/**
 * Represents the mainpanel of the Output section.
 * Shows tables and visualizations of the analysis selected in the sidepanel.
 * @author Bjorn van der Laan
 *
 */
public class OutputMainPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5652640933659529127L;
    private Map<String, Object> map;

    /**
     * Constructor.
     */
    public OutputMainPanel() {
        super();
        this.setLayout(new BorderLayout());

        JTabbedPane pane = new JTabbedPane();
        pane.setBackground(Color.WHITE);
        this.add(pane, BorderLayout.CENTER);
    }

    /**
     * Sets the data of the panel based on the input.
     * @param map2 Map containing the data
     */
    public final void setData(final Map<String, Object> map2) {
        this.map = map2;
        JTabbedPane pane = new JTabbedPane();
        for (String key : map2.keySet()) {
            Object element = map2.get(key);
            if (element instanceof Table) {
                Table table = (Table) element;
                JTable jtable = table.toJTable();
                jtable.setEnabled(false);
                jtable.setAutoCreateRowSorter(true);
                JScrollPane scroll = new JScrollPane(jtable);
                scroll.setHorizontalScrollBar(new JScrollBar());
                pane.add("Tab", scroll);
            }
            else if (element instanceof Component) {
                Component component = (Component) element;
                pane.add(component);
            }
            else if (element instanceof JTable) {
                JTable jtable = (JTable) element;
                jtable.setEnabled(false);
                jtable.setAutoCreateRowSorter(true);
                JScrollPane scroll = new JScrollPane(jtable);
                pane.add("Matrix", scroll);
            }
        }
        this.removeAll();
        this.add(pane);

        this.repaint();
        this.revalidate();
    }
}
