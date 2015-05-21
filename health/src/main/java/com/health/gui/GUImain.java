package com.health.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * The main function for the GUI which initiates the frame.
 *
 * @author daan
 *
 */
public class GUImain extends JFrame {

	/**
	 * PATHTOXMLFORMAT is the path to all config xmls.
	 */
	public static final String PATHTOXMLFORMATS = "data/configXmls/";

	/**
	 * Starts up GUI mainly for development.
	 *
	 * @param args
	 *            arguments for constructor.
	 */
	public static void main(final String[] args) {

		new GUImain();

	}

	/**
	 * Makes the frame and and fills tabs.
	 */
	public GUImain() {
		// intialize the frame
		this.initializeFrame(800, 600);
		// define tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel inputPanel = new InputPanel();
		JPanel outputDataPanel = new OutputDataPanel();
		JPanel outputVisualPanel = new OutputVisualPanel();

		tabbedPane.addTab("Input", inputPanel);
		tabbedPane.addTab("Output Data", outputDataPanel);
		tabbedPane.addTab("Output Visual", outputVisualPanel);

		// add tabs to the pane
		this.add(tabbedPane);

		this.setVisible(true);
	}

	/**
	 * sets the frame variables.
	 */
	private void initializeFrame(final int width, final int height) {
		this.setSize(width, height);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);

		this.setLocation(xPos, yPos);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("kidney");
	}

}
