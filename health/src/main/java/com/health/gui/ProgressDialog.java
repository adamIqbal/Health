package com.health.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressDialog extends JDialog{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6841895954914414908L;
	
	public ProgressDialog(){
		this.setVisible(false);
	}

	public void showDialog(){
        
        // Create Progress Bar
        JLabel msg;
        JProgressBar progress;
        final int MAXIMUM = 100;
        JPanel panel;
        
        progress = new JProgressBar(0,MAXIMUM);
        progress.setIndeterminate(true);
        msg = new JLabel("Processing..");
        
        panel = new JPanel(new BorderLayout(5,5));
        panel.add(msg, BorderLayout.PAGE_START);
        panel.add(progress, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(11,11,11,11));
        
        this.getContentPane().add(panel);
        this.setResizable(false);
        this.pack();
        this.setSize(500, this.getHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setAlwaysOnTop(false);
        this.setVisible(true);   
    }
    
    public void hideDialog() {
    	this.setVisible(false);
    }
	
}
