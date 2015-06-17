package com.health.gui.input.xmlwizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.health.gui.GUImain;

/**
 * TODO delete columns
 * TODO save next to saveAs
 */

/**
 * The frame of the XML Wizard. Contains all panels and controls transition
 * between them.
 * @author Bjorn van der Laan
 */
public class XmlWizard extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -3545571479064583466L;

    private static XmlConfigObject xml;

    private static JPanel cardPanel;

    /**
     * XmlFilePanel static class.
     */
    private static XmlFilePanel filePanel;
    /**
     * XmlEditPanel static class.
     */
    private static XmlEditPanel editPanel;
    /**
     * XmlSavePanel static class.
     */
    private static XmlSavePanel savePanel;

    /**
     * Constructs a XmlWizard containing the wizard panels.
     */
    public XmlWizard() {
        super();
        init();
    }

    /**
     * Initializes the panel contents.
     */
    private void init() {
        this.setLayout(new BorderLayout());

        xml = new XmlConfigObject();

        cardPanel = new JPanel(new CardLayout());
        filePanel = new XmlFilePanel();
        editPanel = new XmlEditPanel();
        savePanel = new XmlSavePanel();
        cardPanel.add(filePanel, "file");
        cardPanel.add(editPanel, "edit");
        cardPanel.add(savePanel, "save");
        this.add(cardPanel, BorderLayout.CENTER);

        JLabel label = new JLabel("Create or edit XML files here");
        this.add(label, BorderLayout.NORTH);

        this.setBackground(GUImain.GUI_COLOR);
        this.setVisible(true);
    }

    /**
     * Moves the CardLayout to the next panel.
     */
    protected static void nextPanel() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.next(cardPanel);
    }

    /**
     * Moves the CardLayout to the previous panel.
     */
    protected static void prevPanel() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.previous(cardPanel);
    }

    /**
     * Resets the state of the panels. Does not work yet.
     */
    protected static void resetPanels() {
        editPanel = new XmlEditPanel();
        savePanel = new XmlSavePanel();
    }

    /**
     * Gets the xml object.
     * @return the xml object
     */
    protected static XmlConfigObject getXml() {
        return xml;
    }

    /**
     * Sets the xml object.
     * @param xml
     *            xml object
     */
    protected static void setXml(final XmlConfigObject xml) {
        XmlWizard.xml = xml;
    }

    /**
     * Gets the filepanel.
     * @return the filepanel
     */
    protected static XmlFilePanel getFilePanel() {
        return filePanel;
    }

    /**
     * Gets the editpanel.
     * @return the editpanel
     */
    protected static XmlEditPanel getEditPanel() {
        return editPanel;
    }

    /**
     * Gets the savepanel.
     * @return the savepanel
     */
    protected static XmlSavePanel getSavePanel() {
        return savePanel;
    }
}
