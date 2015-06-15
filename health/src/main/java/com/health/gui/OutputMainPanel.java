package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
    private HashMap<String, Object> map;

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
     * @param data Map containing the data
     */
    public final void setData(final HashMap<String, Object> data) {
        this.map = data;
        JTabbedPane pane = new JTabbedPane();
        for (String key : data.keySet()) {
            Object element = data.get(key);
            if (element instanceof Table) {
                Table table = (Table) element;
                JTable jtable = table.toJTable();
                jtable.setEnabled(false);
                jtable.setAutoCreateRowSorter(true);
                JScrollPane scroll = new JScrollPane(jtable);
                pane.add("Tab", scroll);
            }
            else if (element instanceof JPanel) {
                JPanel panel = (JPanel) element;
                pane.add(panel);
            }
            else if (element instanceof JFrame) {
                JFrame frame = (JFrame) element;
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                VButton visualButton = new VButton("Show visual");
                visualButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        frame.setVisible(true);
                    }
                });
                panel.add(visualButton, BorderLayout.CENTER);
                pane.add(panel);
            }
        }
        this.removeAll();
        this.add(pane);

        this.repaint();
        this.revalidate();
    }
}
