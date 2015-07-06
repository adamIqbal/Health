package com.health.gui.script;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ScriptPanelSidebar extends JPanel {
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 3261823517410374339L;
	private static JProgressBar progress = new JProgressBar();
	private static JLabel label = new JLabel("");
	
	
	public ScriptPanelSidebar() {
		super();
		this.setLayout(new GridLayout(2,1));
		
		progress.setIndeterminate(true);
		progress.setVisible(false);
		
		this.add(label);
		this.add(progress);
	}
	
	public static void toggleProgress() {
		if(progress.isVisible()) {
			progress.setVisible(false);
			label.setText("");
		}
		else {
			progress.setVisible(true);
			label.setText("Analysis is running. Please wait..");
		}
		
	}

}
