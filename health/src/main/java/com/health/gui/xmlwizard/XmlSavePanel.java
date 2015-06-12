package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

import com.health.gui.GUImain;
import com.health.gui.VButton;

/**
 * Represents the panel where the user can save its new or edited XML Config.
 * file
 * @author Bjorn van der Laan
 *
 */
public class XmlSavePanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -1653095867015812077L;
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
        JButton saveAsButton = new VButton("Save as..");
        saveAsButton.addActionListener(new XmlSaveAsListener());
        JButton resetButton = new VButton("Go back");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                XmlWizard.setXml(new XmlConfigObject());
                XmlWizard.nextPanel();
            }
        });
        buttonPanel.add(resetButton);
        buttonPanel.add(saveAsButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the values of the components of this panel according to the input
     * object.
     */
    public final void setValues() {
        preview.setText(XmlWizard.getXml().toXMLString());

        // if it already has a save button, delete it.
        if (buttonPanel.getComponents().length == 3) {
            buttonPanel.remove(buttonPanel.getComponents()[2]);
        }

        if (XmlWizard.getXml().getPath() != null) {
            JButton saveButton = new VButton("Save");
            saveButton.addActionListener(new XmlSaveListener());
            buttonPanel.add(saveButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }

    private void saveAs() throws IOException {
        String name = JOptionPane.showInputDialog(new JFrame(),
                "Please specify a name for this XML file", "Save as..");
        String filename = GUImain.PATH_TO_CONFIG_XML + name + ".xml";
        File file = new File(filename);

        FileUtils.writeStringToFile(file, XmlWizard.getXml().toXMLString());
    }

    private void saveFile() throws IOException {
        String filename = XmlWizard.getXml().getPath().toString();
        File file = new File(filename);
        FileUtils.writeStringToFile(file, XmlWizard.getXml().toXMLString());

        JOptionPane.showMessageDialog(new JFrame(),
                "Your file has been saved.",
                "Success!", JOptionPane.INFORMATION_MESSAGE);

        XmlWizard.setXml(new XmlConfigObject());
        XmlWizard.nextPanel();
    }

    /**
     * Listens if the user presses the 'Save as..' button.
     * @author Bjorn van der Laan
     *
     */
    private class XmlSaveAsListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                saveAs();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "'Save as..' operation has failed. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Listens if the user presses the Save button.
     * @author Bjorn van der Laan
     *
     */
    private class XmlSaveListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                saveFile();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Save operation has failed. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
