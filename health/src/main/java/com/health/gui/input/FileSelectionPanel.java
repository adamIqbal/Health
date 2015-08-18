package com.health.gui.input;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.health.control.InputData;
import com.health.control.InputLoaderModule;
import com.health.gui.GUImain;
import com.health.gui.ProgressDialog;
import com.health.gui.UserInterface;
import com.health.gui.VButton;

/**
 * Class that makes the file selection panel.
 * @author daan
 *
 */
public class FileSelectionPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -271558376732604213L;
    final ProgressDialog dialog = new ProgressDialog();

    /**
     * Constructor which set the panel layout and adds. components
     */
    public FileSelectionPanel() {
        super();
        
        this.setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel(
                "Drag your files into the window to start!");
        this.add(instructionLabel, BorderLayout.NORTH);

        JScrollPane scrollForFileListing = new JScrollPane(new FileListing());
        this.add(scrollForFileListing, BorderLayout.CENTER);

        VButton addButton = new VButton("Add file");
        ListenForAddFile lforAddFile = new ListenForAddFile();
        addButton.addActionListener(lforAddFile);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);

        
        VButton nextButton = new VButton("Next");
        ListenForNext lnext = new ListenForNext();
        nextButton.addActionListener(lnext);
        buttonPanel.add(nextButton);
        
        
        this.add(buttonPanel, BorderLayout.SOUTH);

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
