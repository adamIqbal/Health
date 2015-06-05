package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.FileType;
import com.health.input.InputDescriptor;
import com.health.input.InputException;

/**
 * Represents the wizard panel where one can specify the delimiters and columns
 * of the Config XML.
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlEditPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 2790653737107250316L;
    private Path xml;
    private XmlStartEditPanel startPanel;
    private XmlColumnEditPanel columnPanel;

    // private JButton backButton;
    private JButton continueButton;

    /**
     * Constructs a XmlEditPanel object
     */
    public XmlEditPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        startPanel = new XmlStartEditPanel();
        this.add(startPanel);

        columnPanel = new XmlColumnEditPanel();
        this.add(columnPanel);

        continueButton = new JButton("Continue");
        this.add(continueButton);
    }

    /**
     * gets the continueButton attribute.
     * 
     * @return JButton continueButton
     */
    public final JButton getContinueButton() {
        return this.continueButton;
    }

    /**
     * Adds an ActionListener to the continueButton.
     * 
     * @param al
     *            ActionListener to be added
     */
    public final void addActionListenerToContinueButton(final ActionListener al) {
        continueButton.addActionListener(al);
    }

    /**
     * Models the input values as a {@link XmlConfigObject} and returns it.
     * 
     * @return XmlConfigObject containing the input values
     */
    public final XmlConfigObject getValues() {
        XmlConfigObject config = new XmlConfigObject();

        config.setType(startPanel.getSelectedType());
        config.setValues(startPanel.getValues(config.getType()));
        config.setColumns(columnPanel.getColumns());
        config.setColumnTypes(columnPanel.getColumnTypes());

        if (this.xml != null) {
            config.setPath(this.xml);
        }

        return config;
    }

    /**
     * Loads current values of the selected XML file en sets the fields of the
     * panel.
     * 
     * @param xml
     *            Path of XML file to edit
     */
    public final void setValues(final Path xml) {
        try {
            InputDescriptor id = new InputDescriptor(xml.toString());

            if (id.getFormat().equals("xlsx") || id.getFormat().equals("xls")) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "XLS support has not been implemented yet.", "Whoops!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String[] values = { id.getStartDelimiter(),
                        id.getEndDelimiter(), id.getDelimiter() };
                startPanel.setValues(values, FileType.TXT);
            }

            // set the columns
            columnPanel.setColumns(id.getColumns(), id.getColumnTypes());
            this.xml = xml;
        } catch (ParserConfigurationException | SAXException | IOException
                | InputException e) {
            System.out.println("Error loading: " + xml.toString());
        }
    }
}