package com.health.gui.xmlwizard.starteditsubpanels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.health.FileType;

/**
 * Represents the panel to specify parameters of the config XML describing an
 * XLS file that are not column-related.
 * @author Bjorn van der Laan
 *
 */
public class XmlXlsEditPanel extends XmlStartEditSubPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -4777267173840186038L;
    private JLabel rowLabel, colLabel, ignoreLastLabel;
    private JTextField rowField, colField, ignoreLastField;

    /**
     * Constructs an XmlXlsEditPanel.
     */
    public XmlXlsEditPanel() {
        super();
        this.setType(FileType.XLS);
        this.setLayout(new GridLayout(0, 2));

        rowLabel = new JLabel("Start row");
        colLabel = new JLabel("Start column");
        ignoreLastLabel = new JLabel("Ignore last lines");
        
        rowField = new JTextField();
        rowField.setHorizontalAlignment(SwingConstants.CENTER);
        colField = new JTextField();
        colField.setHorizontalAlignment(SwingConstants.CENTER);
        ignoreLastField = new JTextField();
        ignoreLastField.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(rowLabel);
        this.add(rowField);
        this.add(colLabel);
        this.add(colField);
        this.add(ignoreLastLabel);
        this.add(ignoreLastField);
    }

    @Override
    public final void setValues(final String[] values) {
        rowField.setText(values[0]);
        colField.setText(values[1]);
        ignoreLastField.setText(values[2]);
    }

    @Override
    public final String[] getValues() {
        final int amountOfValues = 3;

        String[] values = new String[amountOfValues];
        values[0] = this.rowField.getText();
        values[1] = this.colField.getText();
        values[2] = this.ignoreLastField.getText();
        return values;
    }
}
