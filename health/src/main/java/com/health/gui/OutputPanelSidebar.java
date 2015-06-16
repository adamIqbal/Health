package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Represents the sidebar of the Output section.
 * @author Bjorn van der Laan
 *
 */
public class OutputPanelSidebar extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 9050949741413643882L;
    /**
     * Contains all completed visualizations.
     */
    protected static JList<String> list;
    private static Map<String, Map<String, Object>> dataMap;

    /**
     * Constructor.
     */
    public OutputPanelSidebar() {
        super();
        this.setLayout(new BorderLayout());

        dataMap = new HashMap<String, Map<String, Object>>();

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        list = new JList<String>(listModel);
        list.setBackground(Color.white);
        this.add(list, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private static void addElement(final String el) {
        DefaultListModel<String> model = (DefaultListModel<String>) list
                .getModel();
        model.addElement(el);
        list.repaint();
        list.revalidate();
    }

    /**
     * Get data of a past analysis.
     * @param el the stringname of this analysis
     * @return a Map containing the data
     */
    protected static Map<String, Object> getData(final String el) {
        return dataMap.get(el);
    }

    /**
     * Adds a new analysis to the Output section.
     * @param data data of this analysis
     */
    public static void add(final Map<String, Object> data) {
        Date date = new Date();
        String name = "Analysis " + date.toString();
        OutputPanelSidebar.addElement(name);
        dataMap.put(name, data);
    }
}
