package com.health.gui.xmlwizard.starteditsubpanels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.health.FileType;

/**
 * Represents the panel one can edit the delimiters.
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlTxtEditPanel extends XmlStartEditSubPanel {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = -4583356958318824201L;
    private JTextField startDelimField, endDelimField, delimiterField, ignoreLastField;
    private JLabel startDelimLabel, endDelimLabel, delimiterLabel, ignoreLastLabel;

    /**
     * Constructs a XmlTxtEditPanel object.
     */
    public XmlTxtEditPanel() {
        super();

        this.setType(FileType.TXT);

        // Preferred dimensions for the textfields
        // preferredDim = new Dimension(200,50);

        this.setLayout(new GridLayout(0, 2));
        // Create labels for delimiters
        startDelimLabel = new JLabel("Start Delimiter");
        endDelimLabel = new JLabel("End Delimiter");
        delimiterLabel = new JLabel("Delimiter");
        ignoreLastLabel = new JLabel("Ignore last lines");
        
        startDelimField = new JTextField();
        startDelimField.setHorizontalAlignment(SwingConstants.CENTER);
        
        endDelimField = new JTextField();
        endDelimField.setHorizontalAlignment(SwingConstants.CENTER);
        
        delimiterField = new JTextField();
        delimiterField.setHorizontalAlignment(SwingConstants.CENTER);
        
        delimiterField = new JTextField();
        delimiterField.setHorizontalAlignment(SwingConstants.CENTER);
        
        ignoreLastField = new JTextField();
        ignoreLastField.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add to delimiter panel
        this.add(startDelimLabel);
        this.add(startDelimField);
        this.add(delimiterLabel);
        this.add(delimiterField);
        this.add(endDelimLabel);
        this.add(endDelimField);
        this.add(ignoreLastLabel);
        this.add(ignoreLastField);
        
    }

    @Override
    public final void setValues(final String[] values) {
        if (values != null) {
            this.startDelimField.setText(values[0]);
            this.endDelimField.setText(values[1]);
            this.delimiterField.setText(values[2]);
            this.ignoreLastField.setText(values[3]);
        } else {
            clearValues();
        }
    }

    /**
     * Clears the textfields.
     */
    public final void clearValues() {
        this.startDelimField.setText("");
        this.endDelimField.setText("");
        this.delimiterField.setText("");
    }

    @Override
    public final String[] getValues() {
        final int amountOfValues = 4;

        String[] values = new String[amountOfValues];
        values[0] = this.startDelimField.getText();
        values[1] = this.endDelimField.getText();
        values[2] = this.delimiterField.getText();
        values[3] = this.ignoreLastField.getText();

        return values;
    }
}
