package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

import com.health.gui.GUImain;

/**
 * Represents the panel where the user can save its new or edited XML Config.
 * file
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlSavePanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -1653095867015812077L;
    private XmlConfigObject xml;
    private JTextArea preview;
    private JPanel buttonPanel;

    /**
     * Represents the panel where the user can save the config xml created by
     * the wizard.
     */
    public XmlSavePanel() {
        super();
        this.setLayout(new BorderLayout());

        preview = new JTextArea();
        preview.setEditable(false);
        preview.setLineWrap(true);
        preview.setWrapStyleWord(true);
        preview.setPreferredSize(getPreferredSize());
        this.add(preview, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        JButton saveAsButton = new JButton("Save as..");
        saveAsButton.addActionListener(new XmlSaveAsListener());
        JButton resetButton = new JButton("Go back to files");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XmlWizard.setXml(null);
                XmlWizard.nextPanel();
            }
        });
        buttonPanel.add(saveAsButton);
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the values of the components of this panel according to the input
     * object.
     */
    public final void setValues() {
        preview.setText(XmlWizard.getXml().toXMLString());
    }

    private void saveAs() throws IOException {
        String xmlString = XmlWizard.getXml().toXMLString();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify where to save");

        File target = null;
        int selection = fileChooser.showSaveDialog(this);
        if (selection == JFileChooser.APPROVE_OPTION) {
            target = fileChooser.getSelectedFile();
        }

        if (target != null) {
            FileUtils.writeStringToFile(target, xmlString);
        }
    }
    
    private void saveAs2() throws IOException {
        String name = JOptionPane.showInputDialog(new JFrame(),
                "Please specify a name for this XML file",
                "Save as..");
        String filename = GUImain.PATH_TO_CONFIG_XML + name + ".xml";
        File file = new File(filename);
        
        FileUtils.writeStringToFile(file, XmlWizard.getXml().toXMLString());
    }

    /**
     * Listens if the user presses the 'Save as..' button. Opens a JFileChooser
     * to specify save location. Displays a warning dialog if the save operation
     * fails.
     * @author Bjorn van der Laan
     *
     */
    private class XmlSaveAsListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
           try {
                saveAs2();
           } catch (IOException e1) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "'Save as..' operation has failed. Please try again.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
