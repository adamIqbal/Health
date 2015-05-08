package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.io.File;

public class GUImain extends JFrame{
	
	
	static JPanel fileListing;
	static GridBagConstraints fileListingCons;
	static FileListingRow[] fileListingRows = new FileListingRow[20];
	static int fileCount = 0;
	
	public static void main(String[] args){
		
		new GUImain(); 
		
	}
	
	public GUImain(){
		//intialize the frame
		this.initializeFrame();
		//define tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel inputPanel = this.makeInputTab();
		JPanel outputDataPanel = this.makeOutputDataTab();
		JPanel outputVisualPanel = this.makeOutputVisualTab();
		
		
		tabbedPane.addTab("Input", inputPanel);
		tabbedPane.addTab("Output Data", outputDataPanel);
		tabbedPane.addTab("Output Visual", outputVisualPanel);
		
		//add tabs to the pane
		this.add(tabbedPane);
		
		this.setVisible(true);
	}
	
	private void initializeFrame(){
		this.setSize(800,600);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		
		int xPos = (dim.width/2) - (this.getWidth()/2);
		int yPos = (dim.height/2) - (this.getHeight()/2);
		
		this.setLocation(xPos, yPos);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("kidney");
	}
	
	private JPanel makeInputTab(){
		
		JPanel panel = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel scriptPanel = this.makeScriptTab();
		JPanel fileSelectionPanel = this.makeFileSelectionTab();
		
		tabbedPane.addTab("File Selection", fileSelectionPanel);
		tabbedPane.addTab("Script", scriptPanel);
		
		panel.add(tabbedPane, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel makeFileSelectionTab() {
		JPanel panel = new JPanel(new BorderLayout());
		
		//Set layout and constraints
		fileListing = new JPanel(new GridBagLayout());
		//fileListing.setBorder(BorderFactory.createLineBorder(Color.black));
		fileListingCons = new GridBagConstraints();
		fileListingCons.fill = GridBagConstraints.BOTH;
		fileListingCons.anchor = GridBagConstraints.LINE_START;
		
		
		this.makeHeaderOfListing();
		
		fileListingCons.gridwidth = 1;
		fileListingCons.gridheight = 1;
		
		GUImain.fillFileListing();
		
		panel.add(fileListing, BorderLayout.CENTER);
		
		JButton addButton = new JButton("add file");
		ListenForAddFile lforAddFile = new ListenForAddFile();
		addButton.addActionListener(lforAddFile);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(addButton);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.setBorder(new EmptyBorder(50,93,50,93));
		
		
		return panel;
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

	private JPanel makeScriptTab() {
		JPanel panelRes = new JPanel(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JTextArea scriptArea = new JTextArea(2,1);
		scriptArea.setBorder(BorderFactory.createLineBorder(Color.black));

		panel.add(scriptArea, BorderLayout.CENTER);
		
		JButton startAnalasisButton = new JButton("Start Analyse");
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(startAnalasisButton);
		panel.add(southPanel, BorderLayout.SOUTH);
		
		JTextField textAbove = new JTextField("Script: ");
		textAbove.setEditable(false);
		textAbove.setDisabledTextColor(Color.black);
		
		panel.add(textAbove, BorderLayout.NORTH);
		panel.setBorder(new EmptyBorder(10,80,10,80));
		
		panelRes.add(panel, BorderLayout.CENTER);
		
		
		return panelRes;
	}

	private JPanel makeOutputDataTab(){
		
		JPanel panel = new JPanel();
		JTextField textField1 = new JTextField("OutputData");
		panel.add(textField1);
		
		return panel;
	}
	
	private JPanel makeOutputVisualTab(){
		
		JPanel panel = new JPanel();
		JTextField textField1 = new JTextField("OutputVisual");
		panel.add(textField1);
		
		return panel;
	}
	
	private class FileListingRow{
		
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
		
		private void fillComboBox(){
			//TO DO : fill combobox with xml formats provided in folder
			
			String[] formats  = {"select format","textFormat", "fooFormat", "faaFormat"};
			xmlFormat = new JComboBox<String>(formats);
		}
		
	}
	
	private class ListenForAddFile implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(GUImain.fileListing);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    FileListingRow row = new FileListingRow();
			    
				row.setFileString(selectedFile.getPath());
				fileListingRows[GUImain.fileCount] = row;
				GUImain.fileCount++;
				GUImain.fillFileListing();
			}
			
				
		}
		
	}
	
	
}