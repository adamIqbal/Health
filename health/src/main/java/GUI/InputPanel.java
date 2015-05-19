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
		
		tabbedPane.addTab("File Selection", fileSelectionPanel);
		tabbedPane.addTab("Script", scriptPanel);
		
		this.add(tabbedPane, BorderLayout.CENTER);
		

	}
	
}
