package com.health.gui;

import java.awt.FontMetrics;
import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.health.control.InputController;
import com.health.control.ScriptController;
import com.health.gui.input.VInputPanel;
import com.health.gui.output.VOutputPanel;
import com.health.gui.script.VScriptPanel;

/**
 * Represents the GUI.
 *
 * @author Daan Vermunt and Bjorn van der Laan
 *
 */
public class GUImain extends JFrame implements UserInterface {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 4671877202500940942L;
    private static Map<String, VidneyPanel> panelMap = new HashMap<String, VidneyPanel>();
    private static JTabbedPane tabbedPane;

    /**
     * Gets a panel by name.
     * @param name
     *            name of the panel
     * @return the panel, or null if it does not exist
     */
    public static VidneyPanel getPanel(final String name) {
        return panelMap.get(name);
    }

    /**
     * Transitions the application to the specified tab.
     * @param name
     *            the name of the tab
     */
    public static void goToTab(final String name) {
        tabbedPane.setSelectedComponent(getPanel(name));
    }

    /**
     * Width of the tabs.
     */
    private final int tabWidth = 200;

    /**
     * Height of the tabs.
     */
    private final int tabHeight = 50;

    /**
     * Makes the frame and and fills tabs.
     */
    public GUImain(String title, String laf) {
        init(title, laf);
    }

    /**
     * Initializes the GUI frame.
     */
    @Override
    public final void init(String title, String laf) {
        this.createTabbedPane();

        this.add(tabbedPane);

        try {
            setLookAndFeel(laf);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    new JFrame(),
                    "Error loading the look and feel. Message: "
                            + e.getMessage(), "Error!",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Adds a tab to the GUI.
     */
    private void addTab(final String name, final VidneyPanel panel) {
        tabbedPane.addTab(name, panel);
        panelMap.put(name, panel);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        VInputPanel inputPanel = new VInputPanel();
        VScriptPanel scriptPanel = new VScriptPanel();
        VidneyPanel outputPanel = new VOutputPanel();
        
        InputController inputController = new InputController(inputPanel);
        ScriptController scriptController = new ScriptController(scriptPanel);

        addTab("Step 1: Input", inputPanel);
        addTab("Step 2: Script", scriptPanel);
        addTab("Step 3: Output", outputPanel);

        tabbedPane.setBackground(UserInterface.GUI_COLOR);
        tabbedPane.setEnabledAt(0,true);
        tabbedPane.setEnabledAt(1,false);
        tabbedPane.setEnabledAt(2,false);
        sizeTabs(tabWidth, tabHeight);
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
    protected void setLookAndFeel(final String name)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        boolean lafFound = false;
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (name.equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                lafFound = true;
                break;
            }
        }
        if (!lafFound) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
    }

    private void sizeTabs(final int width, final int height) {
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabHeight(final int tabPlacement,
                    final int tabIndex, final int fontHeight) {
                return height;
            }

            @Override
            protected int calculateTabWidth(final int tabPlacement,
                    final int tabIndex, final FontMetrics metrics) {
                return width;
            }
        });
    }

    public static void selectedTab(int from, int to){
    	tabbedPane.setEnabledAt(from, false);
    	tabbedPane.setEnabledAt(to, true);
    }
}
