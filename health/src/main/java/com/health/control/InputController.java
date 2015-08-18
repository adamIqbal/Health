package com.health.control;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import com.health.gui.GUImain;
import com.health.gui.ProgressDialog;
import com.health.gui.UserInterface;
import com.health.gui.input.FileListing;
import com.health.gui.input.FileListingRow;
import com.health.gui.input.VInputPanel;

public class InputController {
	private VInputPanel inputPanel;
    final ProgressDialog dialog = new ProgressDialog();
	
	public InputController (VInputPanel inputP){
		inputPanel = inputP;
		control();
	}
	
	private void control(){
		ListenForAddFile lforAddFile = new ListenForAddFile();
		inputPanel.getFileSelectionPanel().getAddButton().addActionListener(lforAddFile);
		
		ListenForNext lnext = new ListenForNext();
		inputPanel.getFileSelectionPanel().getNextButton().addActionListener(lnext);
	}
	
    /**
     * Listener for the add file button.
     *
     */
    private class ListenForAddFile implements ActionListener {

        /**
         * Handles the button click.
         *
         * @param e
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(UserInterface.PATH_TO_DATA));
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                FileListing.addFile(fileChooser.getSelectedFile());

            }

        }

    }
	
    private class ListenForNext implements ActionListener {
    	
    	/**
         * Handles the button click.
         *
         * @param e
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
        	Worker work = new Worker();
        	work.execute();
        }
        
        class Worker extends SwingWorker<Object,Object> {

			@Override
			protected Object doInBackground() throws Exception {
				List<InputData> inputData = getInputData();
	        	InputLoaderModule loader = new InputLoaderModule(inputData);
	        	dialog.showDialog();
	        	loader.loadAllData();
	        	
				return null;
			}
			
			@Override 
			protected void done() {
				GUImain.selectedTab(0, 1);
	        	dialog.hideDialog();
	        	GUImain.goToTab("Step 2: Script");
			}	
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
