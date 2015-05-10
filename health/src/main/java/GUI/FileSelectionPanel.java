package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class FileSelectionPanel extends JPanel{
	static JPanel fileListing;
	static GridBagConstraints fileListingCons;
	static FileListingRow[] fileListingRows = new FileListingRow[20];
	static int fileCount = 0;
	
	public FileSelectionPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		this.setLayout(new BorderLayout());
		
		//Set layout and constraints
		fileListing = new JPanel(new GridBagLayout());
		//fileListing.setBorder(BorderFactory.createLineBorder(Color.black));
		fileListingCons = new GridBagConstraints();
		fileListingCons.fill = GridBagConstraints.BOTH;
		fileListingCons.anchor = GridBagConstraints.LINE_START;
		
		
		this.makeHeaderOfListing();
		
		fileListingCons.gridwidth = 1;
		fileListingCons.gridheight = 1;
		
		this.fillFileListing();
		
		this.add(fileListing, BorderLayout.CENTER);
		
		JButton addButton = new JButton("add file");
		ListenForAddFile lforAddFile = new ListenForAddFile();
		addButton.addActionListener(lforAddFile);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(addButton);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setBorder(new EmptyBorder(50,93,50,93));
		
		
	}
	
	
	public static void fillFileListing(){
		for(int i =0; i < fileListingRows.length; i++){
			if(fileListingRows[i] == null){
				//make empty row
				fileListingCons.gridy = i;
				for(int j =0; j <3; j++){
					fileListingCons.gridx = j;
					JTextField textField = new JTextField();
					textField.setSize(200, 30);
					textField.setEditable(false);
					fileListing.add(textField, fileListingCons);
				}
			}else{
				//fill the row
				fileListingCons.gridy = i+1;
				fileListingCons.gridx = 0;
				fileListing.add(fileListingRows[i].fileField, fileListingCons,(i*3) + 1);
				fileListingCons.gridx = 1;
				fileListing.add(fileListingRows[i].xmlFormat, fileListingCons,(i*3) + 2);
				fileListingCons.gridx = 2;
				fileListing.add(fileListingRows[i].deleteButton, fileListingCons,(i*3) + 3);
			}
		}
		fileListing.revalidate();
		fileListing.repaint();
	}

	private void makeHeaderOfListing(){
		fileListingCons.gridx = 0;
		fileListingCons.gridy = 0;
		JTextField tableHeader1 = new JTextField("File");
		tableHeader1.setEnabled(false);
		tableHeader1.setDisabledTextColor(Color.black);
		tableHeader1.setBackground(Color.gray);
		tableHeader1.setPreferredSize(new Dimension(350, 20));
		fileListing.add(tableHeader1);
		
		fileListingCons.gridx = 1;
		JTextField tableHeader2 = new JTextField("Format XML");
		tableHeader2.setEnabled(false);
		tableHeader2.setDisabledTextColor(Color.black);
		tableHeader2.setBackground(Color.gray);
		tableHeader2.setPreferredSize(new Dimension(200, 20));
		fileListing.add(tableHeader2);
		
		fileListingCons.gridx = 2;
		JTextField tableHeader3 = new JTextField("Delete");
		tableHeader3.setEnabled(false);
		tableHeader3.setDisabledTextColor(Color.black);
		tableHeader3.setBackground(Color.gray);
		tableHeader3.setPreferredSize(new Dimension(50, 20));
		fileListing.add(tableHeader3);
	}
	private class ListenForAddFile implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(FileSelectionPanel.fileListing);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    FileListingRow row = new FileListingRow();
			    
				row.setFileString(selectedFile.getPath());
				fileListingRows[FileSelectionPanel.fileCount] = row;
				FileSelectionPanel.fileCount++;
				FileSelectionPanel.fillFileListing();
			}
			
				
		}
		
	}
}
