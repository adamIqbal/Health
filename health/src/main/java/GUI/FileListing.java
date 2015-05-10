package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileListing extends JPanel{
	
	static GridBagConstraints fileListingCons;
	static JPanel listing = new JPanel();
	static FileListingRow[] fileListingRows = new FileListingRow[20];
	static int fileCount = 0;
	
	public FileListing(){
		
		this.setLayout(new BorderLayout());
		
		//Set layout and constraints
		listing.setLayout(new GridBagLayout());
		
		//fileListing.setBorder(BorderFactory.createLineBorder(Color.black));
		fileListingCons = new GridBagConstraints();
		fileListingCons.fill = GridBagConstraints.BOTH;
		fileListingCons.anchor = GridBagConstraints.LINE_START;

		FileListing.fillFileListing();
		
		this.add(listing);
	}
	
	public static void fillFileListing(){
		listing.removeAll();
		makeHeaderOfListing();
		fileListingCons.gridwidth = 1;
		fileListingCons.gridheight = 1;
		for(int i =0; i < fileListingRows.length; i++){
			
			if(fileListingRows[i] == null){
				//make empty row
				fileListingCons.gridy = i+1;
				for(int j =0; j <3; j++){
					fileListingCons.gridx = j;
					JTextField textField = new JTextField();
					textField.setSize(200, 30);
					textField.setEditable(false);
					listing.add(textField, fileListingCons);
				}
			}else{
				//fill the row
				fileListingCons.gridy = i+1;
				fileListingCons.gridx = 0;
				listing.add(fileListingRows[i].fileField, fileListingCons,(i*3) + 1);
				fileListingCons.gridx = 1;
				listing.add(fileListingRows[i].xmlFormat, fileListingCons,(i*3) + 2);
				fileListingCons.gridx = 2;
				listing.add(fileListingRows[i].deleteButton, fileListingCons,(i*3) + 3);
			}
		}
		listing.revalidate();
		listing.repaint();
	}
	
	private static void makeHeaderOfListing(){
		fileListingCons.gridx = 0;
		fileListingCons.gridy = 0;
		JTextField tableHeader1 = new JTextField("File");
		tableHeader1.setEnabled(false);
		tableHeader1.setDisabledTextColor(Color.black);
		tableHeader1.setBackground(Color.gray);
		tableHeader1.setPreferredSize(new Dimension(350, 20));
		listing.add(tableHeader1);
		
		fileListingCons.gridx = 1;
		JTextField tableHeader2 = new JTextField("Format XML");
		tableHeader2.setEnabled(false);
		tableHeader2.setDisabledTextColor(Color.black);
		tableHeader2.setBackground(Color.gray);
		tableHeader2.setPreferredSize(new Dimension(200, 20));
		listing.add(tableHeader2);
		
		fileListingCons.gridx = 2;
		JTextField tableHeader3 = new JTextField("Delete");
		tableHeader3.setEnabled(false);
		tableHeader3.setDisabledTextColor(Color.black);
		tableHeader3.setBackground(Color.gray);
		tableHeader3.setPreferredSize(new Dimension(50, 20));
		listing.add(tableHeader3);
	}
	
	public static void addFile(File newFile){
	    FileListingRow row = new FileListingRow();
	    
		row.setFileString(newFile.getPath());
		fileListingRows[fileCount] = row;
		fileCount++;
		fillFileListing();
	}
	
	public static void delete(String toBeDeleted){
		boolean found = false;
		for(int i =0; i < fileListingRows.length;i++){
			if(!found){
				if(fileListingRows[i].fileString.equals(toBeDeleted)){
					fileListingRows[i] = null;
					found = true;
				}
			}else if(fileListingRows[i] != null){
				fileListingRows[i-1] = fileListingRows[i];
				fileListingRows[i] = null;
			}
		}
		fileCount--;
		
		FileListing.fillFileListing();
	}
	
}
