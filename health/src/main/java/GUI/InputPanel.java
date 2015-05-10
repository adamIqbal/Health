package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import externalClasses.FileDrop;

public class InputPanel extends JPanel{

	public InputPanel(){
		this.setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel scriptPanel = new ScriptPanel();
		JPanel fileSelectionPanel = new FileSelectionPanel();
		
		new FileDrop( fileSelectionPanel, new FileDrop.Listener()
	      {   public void filesDropped( java.io.File[] files )
	          {  
	            for(int i =0; i< files.length; i++){
	            	 FileListing.addFile(files[i]);
	            }
	          }   // end filesDropped
	      }); // end FileDrop.Listener
		
		tabbedPane.addTab("File Selection", fileSelectionPanel);
		tabbedPane.addTab("Script", scriptPanel);
		
		this.add(tabbedPane, BorderLayout.CENTER);
	}
}
