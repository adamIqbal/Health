package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * TODO make it possible to add columns with no label.
 * TODO implement way to delete columns.
 * TODO modify XmlConfigObject. Make attributes private en create getters/setters.
 * TODO back button.
 * TODO FileListing.fillfilelisting gebruiken om te refreshen na save.
 * TODO Na save meteen afsluiten.
 * TODO Save as alleen format naam automatisch naar goed folder en toevoegen .xml
 */

/**
 * Steps to add new dataset type
 * - add it to enum
 * - create a subclass of XmlStartEditSubPanel
 * - write a toXMLString in XmlConfigObject
 * </p>
 */

/**
 * The frame of the XML Wizard. Contains all panels and controls transition
 * between them.
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlWizard extends JPanel {
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

        //XmlWizardListener wizardListener = new XmlWizardListener(this);
        //wizardListener.attachListenerToButtons();

        this.setVisible(true);
    }
    
    protected static void nextPanel() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.next(cardPanel);
    }

    protected static XmlConfigObject getXml() {
        return xml;
    }

    protected static void setXml(XmlConfigObject xml) {
        XmlWizard.xml = xml;
    }

    /**
     * Returns the XmlFilePanel. This method is used by the private class.
     * implementing ActionListener
     * 
     * @return the XmlFilePanel
     */
    protected final XmlFilePanel getFilePanel() {
        return filePanel;
    }

    /**
     * Returns the XmlEditPanel. This method is used by the private class
     * implementing ActionListener
     * 
     * @return the XmlEditPanel
     */
    protected final XmlEditPanel getEditPanel() {
        return editPanel;
    }

    /**
     * Returns the XmlSavePanel. This method is used by the private class.
     * implementing ActionListener
     * 
     * @return the XmlSavePanel
     */
    protected final XmlSavePanel getSavePanel() {
        return savePanel;
    }
    
    /**
     * Attaches the XmlWizardListener to the buttons in the different
     * panels.
     */
    public void attachListenerToButtons() {
        filePanel.addActionListenerToNewFileButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //this.changePanel(editPanel);
            }
        });
        filePanel.addActionListenerToSelectFileButton(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
        editPanel.addActionListenerToContinueButton(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
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