package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import externalClasses.FileDrop;

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
		
		
		deleteButton = new JButton("X");
		lforDelete = new ListenForDeleteFile(fileString);
		deleteButton.addActionListener(lforDelete);
		
		new FileDrop(fileField, fileField.getBorder(),new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (int i = 0; i < files.length; i++) {
					FileListing.addFile(files[i]);
				}
			}
		});
		
		this.fillComboBox();
				
	}
	
	public void setFileString(String fileString){
		this.fileString = fileString;
		String fileFieldText = fileString;
		
		//if string wont fit the field
		if(fileFieldText.length() > 50){
			//trim of till 50 chars
			fileFieldText = this.fileString.substring(fileFieldText.length()-50);
			
			//find last slash that fits in the field
			int lastSlash =fileFieldText.indexOf('/');
			
			//if there is a slash
			if(lastSlash != -1){
				fileFieldText = fileFieldText.substring(lastSlash); 
			}
			fileFieldText = "..." + fileFieldText;
		}
		
		fileField.setText(fileFieldText);
		lforDelete.setStringToBeDeleted(this.fileString);
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