package com.health.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.control.ControlModule;
import com.health.gui.fileSelection.FileListing;
import com.health.gui.fileSelection.FileListingRow;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Represents the panel where the script is typed.
 * @author Bjorn van der Laan and Daan Vermunt
 *
 */
public class VScriptPanel extends VidneyPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 4322421568728565558L;
    private JTextArea scriptArea;

    /**
     * Constructor.
     */
    public VScriptPanel() {
        super();

        JPanel mainPanel = new JPanel(new BorderLayout());

        scriptArea = new JTextArea(2, 1);
        scriptArea.setText("");
        mainPanel.add(scriptArea, BorderLayout.CENTER);

        JButton startAnalysisButton = new JButton("Start Analysis");
        startAnalysisButton.addActionListener(new AnalysisListener());
        mainPanel.add(startAnalysisButton, BorderLayout.SOUTH);

        JLabel textAbove = new JLabel("Script: ");
        mainPanel.add(textAbove, BorderLayout.NORTH);

        this.setLeft(mainPanel);
        
        JPanel sidePanel = new JPanel();
        sidePanel.add(new ScriptPanelSidebar());
        this.setRight(sidePanel);
    }

    /**
     * Gets the script as a String.
     * @return a String representation of the script
     */
    protected String getScriptAreaText() {
        return scriptArea.getText();
    }

    /**
     * Starts the analysis when the button is clicked.
     * @author Daan Vermunt
     */
    private class AnalysisListener implements ActionListener {

        public AnalysisListener() {
            super();
        }

        /**
         * Makes inputData array and calls the control module.
         *
         * @param event
         */
        public void actionPerformed(final ActionEvent event) {
            List<FileListingRow> files = FileListing.getFileListingRows();
            List<Table> parsedData = new ArrayList<Table>();

            for (int i = 0; i < files.size(); i++) {
                String xmlFormat = files.get(i).getXmlFormat()
                        .getSelectedItem().toString();
                String fileString = files.get(i).getFileString();
                xmlFormat = GUImain.PATH_TO_CONFIG_XML + xmlFormat + ".xml";

                try {
                    parsedData.add(Input.readTable(fileString, xmlFormat));
                } catch (IOException | ParserConfigurationException
                        | SAXException | InputException e) {
                    System.out
                            .println("Error: Something went wrong parsing the config and data!");

                    e.printStackTrace();
                }
            }
            
            VScriptPanel scriptPanel = (VScriptPanel) GUImain.getPanel("script");
            String script = scriptPanel.getScriptAreaText();

            ControlModule control = new ControlModule(script, parsedData);

            String output = null;

            try {
                output = control.startAnalysis();
            } catch (Exception e) {
                e.printStackTrace();

                output = e.getMessage();

                JOptionPane
                        .showMessageDialog(
                                null,
                                String.format(
                                        "An unhandled exception occured while executing the script: %s.",
                                        output), "Script runtime exception",
                                JOptionPane.ERROR_MESSAGE);
            }

            VOutputPanel.displayData(output);

        }
    }

}
