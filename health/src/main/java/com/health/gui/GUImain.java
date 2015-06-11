package com.health.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Represents the GUI.
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
    public static final String PATH_TO_CONFIG_XML = "data/configXmls/";

    /**
     * Main color of the GUI.
     */
    public static final Color GUI_COLOR = new Color(137, 207, 240);

    private static Map<String, VidneyPanel> panelMap;
    private JTabbedPane tabbedPane;

    /**
     * Width of the GUI.
     */
    private final int width = 1000;
    /**
     * Height of the GUI.
     */
    private final int height = 618;

    /**
     * Makes the frame and and fills tabs.
     */
    public GUImain() {
        panelMap = new HashMap<String, VidneyPanel>();

        this.initializeFrame();

        tabbedPane = new JTabbedPane();
        VidneyPanel inputPanel = new VInputPanel();
        VidneyPanel scriptPanel = new VScriptPanel();
        VidneyPanel outputPanel = new VOutputPanel();

        addTab("Input", inputPanel);
        addTab("Script", scriptPanel);
        addTab("Output", outputPanel);

        tabbedPane.setBackground(GUImain.GUI_COLOR);
        this.add(tabbedPane);

        try {
            setLookAndFeel("Metal");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(
                    new JFrame(),
                    "Error loading the look and feel. Message: "
                            + e.getMessage(), "Error!",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        this.setVisible(true);
    }

    /**
     * Adds a tab to the GUI.
     */
    private void addTab(final String name, final VidneyPanel panel) {
        tabbedPane.addTab(name, panel);
        panelMap.put(name, panel);
    }

    /**
     * Sets look and feel of the application.
     * @param name
     *            Name of the lookandfeel
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private void setLookAndFeel(final String name) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        boolean nimbusFound = false;
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (name.equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                nimbusFound = true;
                break;
            }
        }
        if (!nimbusFound) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
    }

    /**
     * sets the frame variables.
     */
    private void initializeFrame() {
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

    /**
     * Gets a panel by name.
     * @param name name of the panel
     * @return the panel, or null if it does not exist
     */
    public static VidneyPanel getPanel(final String name) {
        return panelMap.get(name);
    }

}
