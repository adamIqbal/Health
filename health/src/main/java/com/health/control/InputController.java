package com.health.control;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.gui.GUImain;
import com.health.gui.ProgressDialog;
import com.health.gui.UserInterface;
import com.health.gui.input.FileListing;
import com.health.gui.input.FileListingRow;
import com.health.gui.input.VInputPanel;
import com.health.input.Input;
import com.health.input.InputException;

public class InputController {
	private VInputPanel inputPanel;
    final ProgressDialog dialog = new ProgressDialog();
    private Map<String,Table> map;
	
	public InputController (VInputPanel inputP){
		inputPanel = inputP;
		map = new HashMap<String,Table>();
	}
	
	public void control(){
		ListenForAddFile lforAddFile = new ListenForAddFile();
		inputPanel.getFileSelectionPanel().getAddButton().addActionListener(lforAddFile);
		
		ListenForNext lnext = new ListenForNext();
		inputPanel.getFileSelectionPanel().getNextButton().addActionListener(lnext);
	}
		
    private void loadTables(List<InputData> data) {
    	map.clear();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                this.loadTable(data.get(i));
            }
        }
    }

    public Map<String,Table> getTables(){
    	return map;
    }
    
    private void loadTable(final InputData input) {
        try {
            Table table = Input.readTable(input.getFilePath(), input.getConfigPath());
            map.put(input.getName(),table);
            
        } catch (IOException | ParserConfigurationException
                | SAXException | InputException e) {
            System.out.println("Error: Something went wrong parsing the config and data!");

            e.printStackTrace();

            return;
        }
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
	        	dialog.showDialog();
	        	
	        	loadTables(getInputData());
	        	
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
