package com.health.gui.input.xmlwizard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.FileType;
import com.health.gui.VButton;
import com.health.input.InputDescriptor;
import com.health.input.InputException;

/**
 * Represents the wizard panel where one can specify the delimiters and columns
 * of the Config XML.
 * @author Bjorn van der Laan
 *
 */
public class XmlEditPanel extends JPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 2790653737107250316L;
    private XmlStartEditPanel startPanel;
    private XmlColumnEditPanel columnPanel;

    // private JButton backButton;
    private JButton continueButton;

    /**
     * Constructs a XmlEditPanel object.
     */
    public XmlEditPanel() {
        super();
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        startPanel = new XmlStartEditPanel();
        this.add(startPanel, BorderLayout.NORTH);
        columnPanel = new XmlColumnEditPanel();
        this.add(columnPanel, BorderLayout.CENTER);

        continueButton = new VButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                XmlWizard.setXml(getValues());
                XmlWizard.getSavePanel().setValues();
                XmlWizard.nextPanel();
            }
        });
        this.add(continueButton, BorderLayout.SOUTH);
    }

    /**
     * Models the input values as a {@link XmlConfigObject} and returns it.
     * @return XmlConfigObject containing the input values
     */
    public final XmlConfigObject getValues() {
        XmlConfigObject config = new XmlConfigObject();

        config.setType(startPanel.getSelectedType());
        config.setValues(startPanel.getValues(config.getType()));
        config.setColumns(columnPanel.getColumns());
        config.setColumnTypes(columnPanel.getColumnTypes());
        config.setPath(XmlWizard.getXml().getPath());

        return config;
    }

    /**
     * Loads current values of the selected XML file en sets the fields of the
     * panel.
     */
    public final void setValues() {
        XmlConfigObject xml = XmlWizard.getXml();
        if (xml.getPath() != null) {
            try {
                // I know. But using it to parse the xml
                InputDescriptor id = new InputDescriptor(xml.getPath()
                        .toString());

                XmlConfigObject xmlObj = XmlWizard.getXml();
                xmlObj.setDateFormat(id.getDateFormat());
                XmlWizard.setXml(xmlObj);

                if (id.getFormat().equals("xls")) {
                    String[] values = {
                            Integer.toString(id.getStartCell().getStartRow()),
                            Integer.toString(id.getStartCell().getStartColumn()),
                            Integer.toString(id.getIgnoreLast()) };
                    startPanel.setValues(values, FileType.XLS);
                    startPanel.setFileType(FileType.XLS);
                } 
                else if (id.getFormat().equals("xlsx")) {
                    String[] values = {
                            Integer.toString(id.getStartCell().getStartRow()),
                            Integer.toString(id.getStartCell().getStartColumn()),
                            Integer.toString(id.getIgnoreLast()) };
                    startPanel.setValues(values, FileType.XLSX);
                    startPanel.setFileType(FileType.XLSX);
                }
                else {
                    // default case: txt
                    String[] values = { id.getStartDelimiter(),
                            id.getEndDelimiter(), id.getDelimiter(),
                            Integer.toString(id.getIgnoreLast()) };
                    startPanel.setValues(values, FileType.TXT);
                    startPanel.setFileType(FileType.TXT);
                }

                columnPanel.setColumns(id.getColumns(), id.getColumnTypes());
            } catch (ParserConfigurationException | SAXException | IOException
                    | InputException e) {
                XmlWizard.prevPanel();
                JOptionPane.showMessageDialog(new JFrame(),
                        "The file is not found. Error: " + e.getMessage(),
                        "Error loading file", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // case where new xml is created
            startPanel.setValues(null, FileType.TXT);
            columnPanel.clearColumns();
        }
    }
}
