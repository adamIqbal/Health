package com.health.gui.script;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import com.health.control.ControlModule;
import com.health.control.InputData;
import com.health.gui.GUImain;
import com.health.gui.UserInterface;
import com.health.gui.VButton;
import com.health.gui.VidneyPanel;
import com.health.gui.input.FileListing;
import com.health.gui.input.FileListingRow;
import com.health.gui.output.VOutputPanel;
import com.health.script.runtime.Context;

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

    /**
     * Constructor.
     */
    public VScriptPanel() {
        super();

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(new ScriptMainPanel(), BorderLayout.CENTER);
        
        VButton startAnalysisButton = new VButton("Run Analysis");
        startAnalysisButton.addActionListener(new AnalysisListener());
        startAnalysisButton.setBackground(Color.GREEN);
        
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
        topPanel.add(rigidArea());
        topPanel.add(startAnalysisButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        this.setLeft(mainPanel);

        ScriptPanelSidebar sidePanel = new ScriptPanelSidebar();
        this.setRight(sidePanel);
    }

    private VButton createSaveButton() {
        VButton saveScriptButton = new VButton("Save Script");
        saveScriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (ScriptMainPanel.getScript().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not typed a script yet.", "Whoops!",
                            JOptionPane.OK_OPTION);
                } else {
                    String name = JOptionPane.showInputDialog(new JFrame(),
                            "Please specify a name for this script file",
                            "Save as..");
                    String filename = UserInterface.PATH_TO_DATA + "scripts/" + name
                            + ".txt";
                    File file = new File(filename);

                    try {
                        FileUtils.writeStringToFile(file,
                                ScriptMainPanel.getScript());
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
        return Box.createRigidArea(new Dimension(10, 0));
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
                loadFile.setCurrentDirectory(new File(UserInterface.PATH_TO_DATA));
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
        ScriptMainPanel.setScript(text);
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
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (ScriptMainPanel.getScript().equals("")) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "The script is empty.", "Whoops!",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            try {            	
                startAnalysis();
            } catch (Exception e) {
                e.printStackTrace();

                JOptionPane
                        .showMessageDialog(
                                null,
                                String.format(
                                        "An unhandled exception occured while executing the script: %s.",
                                        e.getMessage()),
                                "Script runtime exception",
                                JOptionPane.ERROR_MESSAGE);

                return;
            } 
        }
        
        private void startAnalysis() {
        	  class MyWorker extends SwingWorker<String, Object> {
        		  Context context;
        		  ControlModule control;
        		  
        	     protected String doInBackground() throws IOException {
        	       ScriptPanelSidebar.toggleProgress();
        	       
                   control = new ControlModule();
                   control.setScript(ScriptMainPanel.getScript());
                   control.setData(getInputData());
                   context = control.startAnalysis();
                   
        	       return "Done.";
        	     }

        	     protected void done() {
        	    	 ScriptPanelSidebar.toggleProgress();
        	    	 VOutputPanel.addAnalysis(control.getOutput());
        	    	 
        	    	 GUImain.goToTab("Step 3: Output");
                     JOptionPane.showMessageDialog(new JFrame(),
                             "Analysis is done.", "Done!",
                             JOptionPane.INFORMATION_MESSAGE);
        	     }
        	  }

        	  new MyWorker().execute();

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

                xmlFormat = UserInterface.PATH_TO_CONFIG_XML + xmlFormat + ".xml";

                parsedData.add(new InputData(fileString, xmlFormat, name));
            }

            return parsedData;
        }
    }
}
