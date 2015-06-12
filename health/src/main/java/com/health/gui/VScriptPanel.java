package com.health.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.health.control.ControlModule;
import com.health.control.InputData;
import com.health.gui.fileSelection.FileListing;
import com.health.gui.fileSelection.FileListingRow;

/**
 * Represents the panel where the script is typed.
 *
 * @author Bjorn van der Laan and Daan Vermunt
 *
 */
public final class VScriptPanel extends VidneyPanel {
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

        VButton startAnalysisButton = new VButton("Start Analysis");
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
     *
     * @return a String representation of the script
     */
    protected String getScriptAreaText() {
        return scriptArea.getText();
    }

    /**
     * Starts the analysis when the button is clicked.
     *
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
            ControlModule control = new ControlModule();
            control.setScript(getScript());
            control.setData(getInputData());

            String output = null;

            try {
                output = control.startAnalysis();
            } catch (Exception e) {
                e.printStackTrace();

                output = e.getMessage();

                JOptionPane.showMessageDialog(
                        null,
                        String.format("An unhandled exception occured while executing the script: %s.", output),
                        "Script runtime exception",
                        JOptionPane.ERROR_MESSAGE);
            }

            VOutputPanel.displayData(output);
        }

        private String getScript() {
            VScriptPanel scriptPanel = (VScriptPanel) GUImain.getPanel("Step 2: Script");
            String script = scriptPanel.getScriptAreaText();

            return script;
        }

        private List<InputData> getInputData() {
            List<FileListingRow> files = FileListing.getFileListingRows();
            List<InputData> parsedData = new ArrayList<InputData>();

            for (int i = 0; i < files.size(); i++) {
                String xmlFormat = files.get(i).getXmlFormat().getSelectedItem().toString();
                String fileString = files.get(i).getFileString();

                //TODO: Name the tables to something other than table0 ... tableN
                String name = "table" + i;

                xmlFormat = GUImain.PATH_TO_CONFIG_XML + xmlFormat + ".xml";

                parsedData.add(new InputData(fileString, xmlFormat, name));
            }

            return parsedData;
        }
    }

}
