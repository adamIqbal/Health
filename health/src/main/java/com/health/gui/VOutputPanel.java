package com.health.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Represents the panel where the script is typed.
 * @author Bjorn van der Laan and Daan Vermunt
 *
 */
public class VOutputPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -5303011708825739028L;

    /**
     * Constructor.
     */
    public VOutputPanel() {
        super();

        OutputMainPanel mainPanel = new OutputMainPanel();
        this.setLeft(mainPanel);
        
        OutputPanelSidebar sidebar = new OutputPanelSidebar();
        OutputPanelSidebar.list.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String selected = OutputPanelSidebar.list.getSelectedValue();
                mainPanel.setData(OutputPanelSidebar.getData(selected));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {   
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
        });
        this.setRight(sidebar);
        
        //
        String filePath = "data/data_all/data_txt/ADMIRE 2.txt";
        String configPath = "data/configXmls/admireTxtConfig.xml";
        Table table = null;
        try {
            table = Input.readTable(filePath, configPath);
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> test = new HashMap<String, Object>();
        test.put("test", table);
        addAnalysis(test);
        //
    }

    /**
     * Same as addAnalysis. Is added for compatibility. 
     * Can be removed later.
     */
    public static void displayData(HashMap<String, Object> data) {
        OutputPanelSidebar.add(data);
    }
    
    /**
     * Adds an performed analysis to the output panel.
     * @param data
     */
    public static void addAnalysis(HashMap<String, Object> data) {
        OutputPanelSidebar.add(data);
    }
}
