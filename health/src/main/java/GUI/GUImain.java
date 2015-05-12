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
	
	
	
	
	public static void main(String[] args){
		
		new GUImain(); 
		
	}
	
	public GUImain(){
		//intialize the frame
		this.initializeFrame();
		//define tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel inputPanel = new InputPanel();
		JPanel outputDataPanel = new OuputDataPanel();
		JPanel outputVisualPanel = new OutputVisualPanel();
		
		
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

	
	
	
	
	
}