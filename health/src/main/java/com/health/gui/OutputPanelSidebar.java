package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class OutputPanelSidebar extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 9050949741413643882L;
    private static JList<String> list;
    private static Map<String, Map<String, Object> > dataMap;
    
    public OutputPanelSidebar() {
        super();
        this.setLayout(new BorderLayout());
        
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        list = new JList(listModel);
        list.setBackground(Color.white);
        list.addMouseListener(new ListListener());
        this.add(list, BorderLayout.CENTER);
        
        this.addElement("efe");
        this.setVisible(true);
    }
    
    private static void addElement(String el) {
        DefaultListModel<String> model =  (DefaultListModel<String>) list.getModel();
        model.addElement(el);
        list.repaint();
        list.revalidate();
    }
    
    private void setMainData() {
        
    }
    
    private class ListListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent arg0) { 
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {        
            }
    }

    public static void add(Map<String, Object> data) {
        Date date = new Date();
        String name = "Analysis" + date.toString();
        OutputPanelSidebar.addElement(name);
        dataMap.put(name, data);
    }
}
