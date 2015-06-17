package com.health.gui.output;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.health.Table;

/**
 * Represents the mainpanel of the Output section. Shows tables and
 * visualizations of the analysis selected in the sidepanel.
 * @author Bjorn van der Laan
 *
 */
public class OutputMainPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5652640933659529127L;
    /**
     * Tabbed pane containing the generated results.
     */
    public static JTabbedPane pane = new JTabbedPane();

    /**
     * Constructor.
     */
    public OutputMainPanel() {
        super();
        this.setLayout(new BorderLayout());
        
        pane.setBackground(Color.WHITE);
        this.add(pane, BorderLayout.CENTER);
    }

    /**
     * Sets the data of the panel based on the input.
     * @param map2
     *            Map containing the data
     */
    public static final void setData(final Map<String, Object> map2) {
        pane.removeAll();
        for (String key : map2.keySet()) {
            Object element = map2.get(key);
            if (element instanceof Table) {
                Table table = (Table) element;
                JTable jtable = table.toJTable();
                jtable.setEnabled(false);
                jtable.setAutoCreateRowSorter(true);
                JScrollPane scroll = new JScrollPane(jtable);
                pane.add("Tab", scroll);
            } else if (element instanceof Component) {
                Component component = (Component) element;
                pane.add("Visual", component);
            } else if (element instanceof JTable) {
                JTable jtable = (JTable) element;
                jtable.setEnabled(false);
                jtable.setAutoCreateRowSorter(true);
                JScrollPane scroll = new JScrollPane(jtable);
                pane.add("Matrix", scroll);
            }
        }

        pane.repaint();
        pane.revalidate();
    }
}
