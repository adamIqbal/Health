package com.health.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

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
    
    public static final Color GUI_COLOR = new Color(137, 207, 240);
    
    private static Map<String, VidneyPanel> panelMap;
    
    public static final int width = 1000;
    public static final int height = 618;

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
        panelMap = new HashMap<String, VidneyPanel>();

        this.initializeFrame(width, height);

        JTabbedPane tabbedPane = new JTabbedPane();
        VidneyPanel inputPanel = new VInputPanel();
        VidneyPanel scriptPanel = new VScriptPanel();
        VidneyPanel outputPanel = new VOutputPanel();

        tabbedPane.addTab("Input", inputPanel);
        tabbedPane.addTab("Script", scriptPanel);
        tabbedPane.addTab("Output", outputPanel);
        
        panelMap.put("input", inputPanel);
        panelMap.put("script", scriptPanel);
        panelMap.put("output", outputPanel);
     
        tabbedPane.setBackground(GUImain.GUI_COLOR);
        this.add(tabbedPane);
        
        try {
            boolean nimbusFound = false;
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusFound = true;
                    break;
                }
            }
            if(!nimbusFound) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            
        } catch (Exception e) {
            
        }


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
    
    public static VidneyPanel getPanel(String name) {
        return panelMap.get(name);
    }

}
