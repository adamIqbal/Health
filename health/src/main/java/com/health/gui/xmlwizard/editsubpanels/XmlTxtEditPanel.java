package com.health.gui.xmlwizard.editsubpanels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.health.FileType;

/**
 * Represents the panel one can edit the delimiters
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlTxtEditPanel extends XmlStartEditSubPanel {
	private JTextField startDelimField, endDelimField, delimiterField;
	private JLabel startDelimLabel, endDelimLabel, delimiterLabel;

	// for some reason it does not work yet.
	// private Dimension preferredDim;

	public XmlTxtEditPanel() {
		super();
		
		type = FileType.TXT;

		// Preferred dimensions for the textfields
		// preferredDim = new Dimension(200,50);

		this.setLayout(new GridLayout(0, 2));
		// Create labels for delimiters
		startDelimLabel = new JLabel("Start Delimiter");
		// startDelimLabel.setPreferredSize(preferredDim);
		endDelimLabel = new JLabel("End Delimiter");
		delimiterLabel = new JLabel("Delimiter");
		// Create input textfields for delimiters
		startDelimField = new JTextField();
		startDelimField.setHorizontalAlignment(SwingConstants.CENTER);
		// startDelimField.setPreferredSize(preferredDim);
		endDelimField = new JTextField();
		endDelimField.setHorizontalAlignment(SwingConstants.CENTER);
		// endDelimField.setPreferredSize(preferredDim);
		delimiterField = new JTextField();
		delimiterField.setHorizontalAlignment(SwingConstants.CENTER);
		// delimiterField.setPreferredSize(preferredDim);
		// Add to delimiter panel
		this.add(startDelimLabel);
		this.add(startDelimField);
		this.add(delimiterLabel);
		this.add(delimiterField);
		this.add(endDelimLabel);
		this.add(endDelimField);
	}

	@Override
	public void setValues(String[] delimiters) {
		//String startDelim, String endDelim, String delim
		this.startDelimField.setText(delimiters[0]);
		this.endDelimField.setText(delimiters[1]);
		this.delimiterField.setText(delimiters[2]);
	}

	@Override
	public String[] getValues() {
		String[] values = new String[3];
		values[0] = this.startDelimField.getText();
		values[1] = this.endDelimField.getText();
		values[2] = this.delimiterField.getText();

		return values;
	}
}