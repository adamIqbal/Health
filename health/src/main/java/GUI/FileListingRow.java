package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class FileListingRow {
	
	public JTextField fileField;
	public String fileString;
	public JComboBox<String> xmlFormat;
	public JButton deleteButton;
	private ListenForDeleteFile lforDelete;
	
	public FileListingRow(){
		fileField = new JTextField();
		fileField.setEnabled(false);
		fileField.setDisabledTextColor(Color.black);
		fileField.setOpaque(false);
		
		
		// TODO handle delete
		deleteButton = new JButton("X");
		lforDelete = new ListenForDeleteFile(fileString);
		deleteButton.addActionListener(lforDelete);
		
		this.fillComboBox();
				
	}
	
	public void setFileString(String fileString){
		this.fileString = fileString;
		fileField.setText(fileString);
		lforDelete.setStringToBeDeleted(fileString);
	}
	
	public void fillComboBox(){
		//TO DO : fill combobox with xml formats provided in folder
		
		String[] formats  = {"select format","textFormat", "fooFormat", "faaFormat"};
		xmlFormat = new JComboBox<String>(formats);
	}
	
	private class ListenForDeleteFile implements ActionListener{
		private String stringToBeDeleted;
		
		public ListenForDeleteFile(String fileString){
			this.stringToBeDeleted = fileString;
		}
		
		public void setStringToBeDeleted(String newFileString){
			this.stringToBeDeleted = newFileString;
		}
		
		public void actionPerformed(ActionEvent e) {
			FileListing.delete(this.stringToBeDeleted);	
		}
		
	}

	
}