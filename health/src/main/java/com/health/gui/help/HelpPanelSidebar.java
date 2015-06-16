package com.health.gui.help;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class HelpPanelSidebar extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 9016045450891366873L;
    private JList<String> topicList;
    private DefaultListModel<String> listModel;
    
    public HelpPanelSidebar() {
        super(new BorderLayout());
        
        listModel = new DefaultListModel<String>();
        topicList = new JList<String>(listModel);
        topicList.setBackground(Color.WHITE);
        this.add(topicList, BorderLayout.CENTER);
    }
    
    protected void addElement(String name) {
        listModel.addElement(name);
    }

    protected JList<String> getTopicList() {
        return topicList;
    }

    protected void setTopicList(JList<String> topicList) {
        this.topicList = topicList;
    }

    protected DefaultListModel<String> getListModel() {
        return listModel;
    }

    protected void setListModel(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }
    
    
}
