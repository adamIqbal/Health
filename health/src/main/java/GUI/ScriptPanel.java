package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ScriptPanel extends JPanel{
	
	public ScriptPanel(){

		this.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel(new BorderLayout());
		
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
		
		this.add(panel, BorderLayout.CENTER);
		
	}
}
