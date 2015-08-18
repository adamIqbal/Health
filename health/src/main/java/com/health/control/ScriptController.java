package com.health.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import com.health.gui.GUImain;
import com.health.gui.ProgressDialog;
import com.health.gui.UserInterface;
import com.health.gui.script.VScriptPanel;

public class ScriptController {
	private VScriptPanel scriptPanel;
    final ProgressDialog dialog = new ProgressDialog();
    private OutputController outputController;
    private InputController inputController;
	
	public ScriptController (VScriptPanel scriptP){
		scriptPanel = scriptP;
	}
	
	public void control(){
	
		
		scriptPanel.getPrevButton().addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(final ActionEvent arg0) {
    			GUImain.selectedTab(1, 0);
    			GUImain.goToTab("Step 1: Input");
    		}
    	});
		
		scriptPanel.getSaveScriptButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (scriptPanel.getScriptMainPanel().getScript().equals("")) {
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
                                scriptPanel.getScriptMainPanel().getScript());
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Error occured while selecting file.", "Error",
                                JOptionPane.OK_OPTION);
                    }
                }
            }
        });
		
		scriptPanel.getClearScriptButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                scriptPanel.setScriptAreaText("");
            }
        });
		
		scriptPanel.getLoadScriptButton().addActionListener(new ActionListener() {
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
                        scriptPanel.setScript(file);
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
		
		
		scriptPanel.getAnalysisButton().addActionListener(new AnalysisListener());
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
            if (scriptPanel.getScriptMainPanel().getScript().equals("")) {
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
        		  ControlModule control;
        		  
        	     protected String doInBackground() throws IOException {
                   dialog.showDialog();
                     
                   control = new ControlModule();
                   control.setScript(scriptPanel.getScriptMainPanel().getScript());
                   control.setData(inputController.getTables());
                   control.startAnalysis();
                   
        	       return "Done.";
        	     }

        	     protected void done() {
        	    	 dialog.hideDialog();
        	    	 
        	    	 outputController.addAnalysis(control.getOutput());
        	    	 GUImain.selectedTab(1, 2);
        	    	 GUImain.goToTab("Step 3: Output");
                     JOptionPane.showMessageDialog(new JFrame(),
                             "Analysis is done.", "Done!",
                             JOptionPane.INFORMATION_MESSAGE);
        	     }
        	  }

        	  new MyWorker().execute();
        }
	}
    
    public void setOutputController(OutputController controller){
    	outputController = controller;
    }
    
    public void setInputController(InputController controller){
    	inputController = controller;
    }
}
