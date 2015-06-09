package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The frame of the XML Wizard. Contains all panels and controls transition
 * between them.
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlWizard extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -3545571479064583466L;

    private static XmlConfigObject xml;

    private static JPanel cardPanel;
    
    protected static XmlFilePanel filePanel;
    protected static XmlEditPanel editPanel;
    protected static XmlSavePanel savePanel;

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

        this.setVisible(true);
    }
    
    protected static void nextPanel() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.next(cardPanel);
    }
    
    protected static void prevPanel() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.previous(cardPanel);
    }
    
    /**
     * Resets the state of the panels.
     * Does not work yet.
     */
    protected static void resetPanels() {
        editPanel = new XmlEditPanel();
        savePanel = new XmlSavePanel();
    }

    protected static XmlConfigObject getXml() {
        return xml;
    }

    protected static void setXml(XmlConfigObject xml) {
        XmlWizard.xml = xml;
    }

    /**
     * XmlWizardListener handles all transitions between panels of the
     * XmlWizard. It implements the ActionListener interface.
     * 
     * @author Bjorn van der Laan
     *
     */
    /*private class XmlWizardListener implements ActionListener {
        private XmlWizard wizardFrame;

        public XmlWizardListener(final XmlWizard xw) {
            super();
            wizardFrame = xw;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source.equals(filePanel.getNewFileButton())) {
                wizardFrame.changePanel(editPanel);
            } else if (source.equals(filePanel.getSelectFileButton())) {
                if (filePanel.getSelectedFile() != null) {
                    editPanel.setValues(XmlWizard.filePanel.getSelectedFile());
                    wizardFrame.changePanel(editPanel);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not selected a file yet!", "Error!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (source.equals(editPanel.getContinueButton())) {
                XmlConfigObject config = editPanel.getValues();
                savePanel.setValues(config);
                wizardFrame.changePanel(savePanel);
            }
        }

    }*/
}