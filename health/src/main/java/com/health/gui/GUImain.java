package com.health.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * The main function for the GUI which initiates the frame.
 *
 * @author Daan Vermunt and Bjorn van der Laan
 *
 */
public class GUImain extends JFrame {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 4671877202500940942L;
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
        final int width = 1000;
        final int height = 600;

        this.initializeFrame(width, height);

        JTabbedPane tabbedPane = new JTabbedPane();
        VidneyPanel inputPanel = new VInputPanel();
        VidneyPanel scriptPanel = new VScriptPanel();
        VidneyPanel outputPanel = new VOutputPanel();

        tabbedPane.addTab("Input", inputPanel);
        tabbedPane.addTab("Script", scriptPanel);
        tabbedPane.addTab("Output", outputPanel);

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
        this.setTitle("Vidney");
    }

}
