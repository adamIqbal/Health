package com.health.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * Class to display all outputData.
 * 
 * @author daan
 *
 */
public class OutputDataPanel extends JPanel {

	private static JTextArea outputArea;

	/**
	 * Method to display all outputData.
	 */
	public OutputDataPanel() {

		outputArea = new JTextArea(2, 1);
		outputArea.setPreferredSize(new Dimension(700, 500));
		outputArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));

		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 30));

		this.add(outputArea);

	}

	public static void displayData(String result) {
		outputArea.setText(result);
		outputArea.revalidate();
		outputArea.repaint();
	}

}
