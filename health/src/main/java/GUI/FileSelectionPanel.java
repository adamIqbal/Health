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
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import externalClasses.FileDrop;


public class FileSelectionPanel extends JPanel{
	
	
	
	public FileSelectionPanel(){
		this.setLayout(new BorderLayout());
		
		new FileDrop( this, new FileDrop.Listener()
	      {   public void filesDropped( java.io.File[] files )
	          {  
	            for(int i =0; i< files.length; i++){
	            	 FileListing.addFile(files[i]);
	            }
	          }   // end filesDropped
	      }); // end FileDrop.Listener
	
		
		JScrollPane scrolForFileListing = new JScrollPane(new FileListing());
		
		this.add(scrolForFileListing, BorderLayout.CENTER);
		
		JButton addButton = new JButton("add file");
		ListenForAddFile lforAddFile = new ListenForAddFile();
		addButton.addActionListener(lforAddFile);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(addButton);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setBorder(new EmptyBorder(70,85,70,85));
		
		new FileDrop( scrolForFileListing,  new FileDrop.Listener()
	      {   public void filesDropped( java.io.File[] files )
	          {  
	            for(int i =0; i< files.length; i++){
	            	 FileListing.addFile(files[i]);
	            }
	          }   // end filesDropped
	      }); // end FileDrop.Listener
		
		
	}
	
	private class ListenForAddFile implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(null);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			    FileListing.addFile(fileChooser.getSelectedFile());
				
			}
			
				
		}
		
	}
}
