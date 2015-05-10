package GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class FileListingRow {
	
	public JTextField fileField;
	public String fileString;
	public JComboBox<String> xmlFormat;
	public JButton deleteButton;
	
	
	public FileListingRow(){
		fileField = new JTextField();
		fileField.setEnabled(false);
		fileField.setDisabledTextColor(Color.black);
		fileField.setOpaque(false);
		
		// TODO handle delete
		deleteButton = new JButton("X");
		
		this.fillComboBox();
				
	}
	
	public void setFileString(String fileString){
		this.fileString = fileString;
		fileField.setText(fileString);
	}
	
	public void fillComboBox(){
		//TO DO : fill combobox with xml formats provided in folder
		
		String[] formats  = {"select format","textFormat", "fooFormat", "faaFormat"};
		xmlFormat = new JComboBox<String>(formats);
	}
	
}