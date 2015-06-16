package com.health.gui.output;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.gui.VidneyPanel;
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
            public void mouseClicked(final MouseEvent e) {
                String selected = OutputPanelSidebar.list.getSelectedValue();
                OutputMainPanel.setData(OutputPanelSidebar.getData(selected));
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }

            @Override
            public void mousePressed(final MouseEvent e) {
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
            }

        });
        this.setRight(sidebar);

        // DUMMY DATA
        /*
        String filePath = "data/data_all/data_txt/ADMIRE 2.txt";
        String filePath2 = "data/data_all/data_txt/ADMIRE_13.txt";
        String configPath = "data/configXmls/admireTxtConfig.xml";
        Table table = null;
        Table table2 = null;
        try {
            table = Input.readTable(filePath, configPath);
            table2 = Input.readTable(filePath2, configPath);
        } catch (IOException | ParserConfigurationException | SAXException
                | InputException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> test = new HashMap<String, Object>();
        test.put("test", table);
        test.put("test2", table2);
        addAnalysis(test);*/
        //
    }

    /**
     * Same as addAnalysis. Is added for compatibility. Can be removed later.
     */
    public static void displayData(final Map<String, Object> data) {
        addAnalysis(data);
    }

    /**
     * Adds an performed analysis to the output panel.
     * @param data
     */
    public static void addAnalysis(final Map<String, Object> data) {
        OutputPanelSidebar.add(data);
    }
}
