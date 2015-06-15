package com.health.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import com.health.Table;
import com.health.control.ControlModule;
import com.health.control.InputData;
import com.health.gui.fileSelection.FileListing;
import com.health.gui.fileSelection.FileListingRow;
import com.health.script.runtime.Context;
import com.health.script.runtime.LValue;
import com.health.script.runtime.ScriptType;
import com.health.script.runtime.WrapperValue;

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
    private static JTextArea scriptArea;

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

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(rigidArea());
        VButton loadScriptButton = createLoadButton();
        topPanel.add(loadScriptButton);
        topPanel.add(rigidArea());
        VButton clearScriptButton = createClearButton();
        topPanel.add(clearScriptButton);
        topPanel.add(rigidArea());
        VButton saveScriptButton = createSaveButton();
        topPanel.add(saveScriptButton);
        topPanel.add(rigidArea());

        mainPanel.add(topPanel, BorderLayout.NORTH);

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
    protected static String getScriptAreaText() {
        return scriptArea.getText();
    }

    private VButton createSaveButton() {
        VButton saveScriptButton = new VButton("Save Script");
        saveScriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (VScriptPanel.getScriptAreaText().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not typed a script yet.", "Whoops!",
                            JOptionPane.OK_OPTION);
                } else {
                    String name = JOptionPane.showInputDialog(new JFrame(),
                            "Please specify a name for this script file",
                            "Save as..");
                    String filename = GUImain.PATH_TO_DATA + "scripts/" + name + ".txt";
                    File file = new File(filename);

                    try {
                        FileUtils.writeStringToFile(file,
                                VScriptPanel.getScriptAreaText());
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Error occured while selecting file.", "Error",
                                JOptionPane.OK_OPTION);
                    }
                }
            }
        });
        return saveScriptButton;
    }

    private Component rigidArea() {
        return Box.createRigidArea(new Dimension(50, 0));
    }

    private VButton createClearButton() {
        VButton clearScriptButton = new VButton("Clear Script");
        clearScriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                VScriptPanel.setScriptAreaText("");
            }
        });
        return clearScriptButton;
    }

    private VButton createLoadButton() {
        VButton loadScriptButton = new VButton("Load existing script");

        loadScriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                JFileChooser loadFile = new JFileChooser();
                loadFile.setApproveButtonText("Select File");
                loadFile.setCurrentDirectory(new File(GUImain.PATH_TO_DATA));
                loadFile.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter f1 = new FileNameExtensionFilter(
                        "Text Files", "txt", "text", "rtf");
                loadFile.setFileFilter(f1);

                switch (loadFile.showOpenDialog(new JFrame())) {
                case JFileChooser.APPROVE_OPTION:
                    File file = loadFile.getSelectedFile();
                    try {
                        VScriptPanel.setScript(file);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Error occured while selecting file", "Error",
                                JOptionPane.OK_OPTION);
                    }
                    break;
                case JFileChooser.ERROR_OPTION:
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Error occured while selecting file", "Error",
                            JOptionPane.OK_OPTION);
                    loadFile.setSelectedFile(null);
                default:
                    break;
                }
            }
        });
        return loadScriptButton;
    }

    /**
     * Sets the script area text.
     * 
     * @param text
     *            the text to set
     */
    private static void setScriptAreaText(final String text) {
        scriptArea.setText(text);
    }

    /**
     * Reads the txt file containing the script.
     * 
     * @param file
     *            File containing the script
     * @throws IOException
     *             thrown if there is an IO error
     */
    protected static void setScript(final File file) throws IOException {
        String script = FileUtils.readFileToString(file);
        VScriptPanel.setScriptAreaText(script);

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
            Context context;
            ControlModule control = new ControlModule();
            control.setScript(getScript());
            control.setData(getInputData());

            try {
                context = control.startAnalysis();
            } catch (Exception e) {
                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        String.format(
                                "An unhandled exception occured while executing the script: %s.",
                                e.getMessage()), "Script runtime exception",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            VOutputPanel.displayData(getOutputData(context, control.getVisuals()));
        }

        private String getScript() {
            return VScriptPanel.getScriptAreaText();
        }

        private List<InputData> getInputData() {
            List<FileListingRow> files = FileListing.getFileListingRows();
            List<InputData> parsedData = new ArrayList<InputData>();

            for (int i = 0; i < files.size(); i++) {
                String xmlFormat = files.get(i).getXmlFormat()
                        .getSelectedItem().toString();
                String fileString = files.get(i).getFileString();

                // TODO: Name the tables to something other than table0 ...
                // tableN
                String name = "table" + i;

                xmlFormat = GUImain.PATH_TO_CONFIG_XML + xmlFormat + ".xml";

                parsedData.add(new InputData(fileString, xmlFormat, name));
            }

            return parsedData;
        }

        private HashMap<String, Object> getOutputData(final Context context, final Map<String, Object> visuals) {
            HashMap<String, Object> output = new HashMap<String, Object>();

            ScriptType tableType = WrapperValue.getWrapperType(Table.class);

            for (Entry<String, LValue> variable : context.getVariables().entrySet()) {
                // Add all table variables to output
                if (variable.getValue().getType() == tableType) {
                    WrapperValue<Table> tableWrapper = (WrapperValue<Table>) variable.getValue().get();
                    Table table = tableWrapper.getValue();

                    output.put(variable.getKey(), table);
                }
            }

            for (Entry<String, Object> visual : visuals.entrySet()) {
                output.put(visual.getKey(), visual.getValue());
            }

            return output;
        }
    }
}
