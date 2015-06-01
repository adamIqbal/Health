package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.control.ControlModule;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * class creates and fills the panel for scripting.
 *
 * @author Daan
 */
public class ScriptPanel extends JPanel {

    private static JTextArea scriptArea;

    /**
     * function creates and fill the panel.
     */
    public ScriptPanel() {

        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        scriptArea = new JTextArea(2, 1);
        scriptArea.setBorder(BorderFactory.createLineBorder(Color.black));

        panel.add(scriptArea, BorderLayout.CENTER);

        JButton startAnalasisButton = new JButton("Start Analysis");
        startAnalasisButton.addActionListener(new ListenForStartAnalysis());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(startAnalasisButton);
        panel.add(southPanel, BorderLayout.SOUTH);

        JTextField textAbove = new JTextField("Script: ");
        textAbove.setEditable(false);
        textAbove.setDisabledTextColor(Color.black);

        panel.add(textAbove, BorderLayout.NORTH);
        panel.setBorder(new EmptyBorder(10, 80, 10, 80));

        this.add(panel, BorderLayout.CENTER);

    }

    /**
     * get the script area component.
     *
     * @return scriptArea the component of the scriptarea.
     */
    public static JTextArea getScriptArea() {
        return scriptArea;
    }

    /**
     * handle start analysis button.
     *
     * @author daan
     *
     */
    private class ListenForStartAnalysis implements ActionListener {

        /**
         * Makes inputData array and calls the control module.
         *
         * @param event
         */
        public void actionPerformed(final ActionEvent event) {
            List<FileListingRow> files = FileListing.getFileListingRows();
            List<Table> parsedData = new ArrayList<Table>();

            for (int i = 0; i < files.size(); i++) {
                String xmlFormat = files.get(i).getXmlFormat().getSelectedItem().toString();
                String fileString = files.get(i).getFileString();
                xmlFormat = GUImain.PATHTOXMLFORMATS + xmlFormat + ".xml";

                try {
                    parsedData.add(Input.readTable(fileString, xmlFormat));
                } catch (IOException | ParserConfigurationException | SAXException | InputException e) {
                    System.out.println("Error: Something went wrong parsing the config and data!");

                    e.printStackTrace();
                }
            }

            String script = ScriptPanel.getScriptArea().getText();

            ControlModule control = new ControlModule(script, parsedData);

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

            OutputDataPanel.displayData(output);

        }
    }
}
