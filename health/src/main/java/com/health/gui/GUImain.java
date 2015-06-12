package com.health.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

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
     * Width of the tabs.
     */
    private final int tabWidth = 330;
    /**
     * Height of the tabs.
     */
    private final int tabHeight = 50;

    /**
     * Makes the frame and and fills tabs.
     */
    public GUImain() {
        panelMap = new HashMap<String, VidneyPanel>();

        this.initializeFrame();

        this.createTabbedPane();

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

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        VidneyPanel inputPanel = new VInputPanel();
        VidneyPanel scriptPanel = new VScriptPanel();
        VidneyPanel outputPanel = new VOutputPanel();

        addTab("Step 1: Input", inputPanel);
        addTab("Step 2: Script", scriptPanel);
        addTab("Step 3: Output", outputPanel);

        tabbedPane.setBackground(GUImain.GUI_COLOR);
        sizeTabs(tabWidth, tabHeight);
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

    private void sizeTabs(final int width, final int height) {
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabWidth(
                    final int tabPlacement, final int tabIndex, final FontMetrics metrics) {
                return width;
            }
            @Override
            protected int calculateTabHeight(final int tabPlacement, final int tabIndex, final int fontHeight) {
                return height;
            }
        });
    }

    /**
     * sets the frame variables.
     */
    private void initializeFrame() {
        /*int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);*/

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
