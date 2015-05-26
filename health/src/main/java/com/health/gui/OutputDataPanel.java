package com.health.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
		this.setLayout(new BorderLayout());

		outputArea = new JTextArea(2, 1);

		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 30));

		JScrollPane scrollforOutputArea = new JScrollPane(outputArea);
		this.add(scrollforOutputArea, BorderLayout.CENTER);

	}

	/**
	 * set the text of the display area.
	 *
	 * @param result
	 *            the result of the analysis
	 */
	public static void displayData(final String result) {
		outputArea.setText(result);
		outputArea.revalidate();
		outputArea.repaint();
	}

}
