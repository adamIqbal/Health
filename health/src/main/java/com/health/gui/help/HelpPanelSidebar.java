package com.health.gui.help;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Represents the sidebar of the help section.
 * @author Bjorn van der Laan
 *
 */
public class HelpPanelSidebar extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 9016045450891366873L;
    private JList<String> topicList;
    private DefaultListModel<String> listModel;

    /**
     * Constructor.
     */
    public HelpPanelSidebar() {
        super(new BorderLayout());

        JLabel label = new JLabel("Help sections");
        label.setBackground(Color.DARK_GRAY);

        listModel = new DefaultListModel<String>();
        topicList = new JList<String>(listModel);
        topicList.setBackground(Color.WHITE);

        this.add(label, BorderLayout.NORTH);
        this.add(topicList, BorderLayout.CENTER);
    }

    /**
     * Adds an element to the list.
     * @param name
     *            name of element
     */
    protected void addElement(String name) {
        listModel.addElement(name);
    }

    /**
     * Gets the list.
     * @return topicList attribute
     */
    protected final JList<String> getTopicList() {
        return topicList;
    }

    /**
     * Sets the list.
     * @param topicList
     *            the list
     */
    protected final void setTopicList(final JList<String> topicList) {
        this.topicList = topicList;
    }

    /**
     * Gets the listModel.
     * @return listModel attribute
     */
    protected final DefaultListModel<String> getListModel() {
        return listModel;
    }

    /**
     * Sets the listModel.
     * @param listModel
     *            ListModel to set
     */
    protected final void setListModel(final DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

}
