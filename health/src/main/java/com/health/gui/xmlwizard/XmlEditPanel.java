package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.ValueType;
import com.health.input.InputDescriptor;
import com.health.input.InputException;

public class XmlEditPanel extends JPanel {
	private Path xml;
	private XmlDelimiterEditPanel delimPanel;
	private XmlColumnEditPanel columnPanel;
	private JButton continueButton;
	
	/**
	 * 
	 * @param xml path to the xml to edit
	 */
	public XmlEditPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		delimPanel = new XmlDelimiterEditPanel();
		this.add(delimPanel, BorderLayout.WEST);
		
		columnPanel = new XmlColumnEditPanel();
		this.add(columnPanel, BorderLayout.CENTER);
		
		continueButton = new JButton("Continue");
		this.add(continueButton, BorderLayout.SOUTH);
	}
	
	public JButton getContinueButton() {
		return this.continueButton;
	}
	
	public void addActionListenerToContinueButton(ActionListener al) {
		continueButton.addActionListener(al);
	}
	
	/**
	 * Loads current values of the selected XML file en sets the fields of the panel
	 * @param xml Path of XML file to edit
	 */
	public void loadCurrentValues(Path xml) {
		try {
			InputDescriptor id = new InputDescriptor(xml.toString());
			delimPanel.setValues(id.getStartDelimiter(), id.getEndDelimiter(), id.getDelimiter());
			columnPanel.setColumns(id.getColumns(), id.getColumnTypes());
			this.xml = xml;
		} catch (ParserConfigurationException | SAXException | IOException
				| InputException e) {
			System.out.println("Error loading: " + xml.toString());
		}
	}
	
	/**
	 * Models the input values as a {@link XmlConfigObject} and returns it
	 * @return XmlConfigObject containing the input values
	 */
	public XmlConfigObject getValues() {
		XmlConfigObject values = new XmlConfigObject();
		String[] delims = delimPanel.getValues();
		values.setDelimiters(delims[0], delims[1], delims[2]);
		
		values.setColumns(columnPanel.getColumns(), columnPanel.getColumnTypes());
		
		if(this.xml != null) {
			values.setPath(this.xml);
		}
		
		return values;
	}
}

class XmlDelimiterEditPanel extends JPanel {
	private JTextField startDelimField, endDelimField, delimiterField;
	private JLabel startDelimLabel, endDelimLabel, delimiterLabel;
	
	public XmlDelimiterEditPanel() {
		super();
		
		this.setLayout(new GridLayout(0,2));
		//Create labels for delimiters
		startDelimLabel = new JLabel("Start Delimiter");
		endDelimLabel = new JLabel("End Delimiter");
		delimiterLabel = new JLabel("Delimiter");
		//Create input textfields for delimiters
		startDelimField = new JTextField();
		startDelimField.setHorizontalAlignment(JTextField.CENTER);
		endDelimField = new JTextField();
		endDelimField.setHorizontalAlignment(JTextField.CENTER);
		delimiterField = new JTextField();
		delimiterField.setHorizontalAlignment(JTextField.CENTER);
		//Add to delimiter panel
		this.add(startDelimLabel);
		this.add(startDelimField);
		this.add(delimiterLabel);
		this.add(delimiterField);
		this.add(endDelimLabel);
		this.add(endDelimField);
	}
	
	public void setValues(String startDelim, String endDelim, String delim) {
		this.startDelimField.setText(startDelim);
		this.endDelimField.setText(endDelim);
		this.delimiterField.setText(delim);
	}
	
	public String[] getValues() {
		String[] values = new String[3];
		values[0] = this.startDelimField.getText();
		values[1] = this.endDelimField.getText();
		values[2] = this.delimiterField.getText();
		
		return values;
 	}
}

class XmlColumnEditPanel extends JPanel {
	public XmlColumnEditPanel() {
		super();
		
		this.setLayout(new GridLayout(0,1));
	}
	
	public void setColumns(List<String> columns, List<ValueType> columnTypes) {
		int size = columns.size();
		if(size != columnTypes.size()) {
			//Error! Lists not same size
		}
		
		for(int i = 0; i < size; i++) {
			JPanel column = new JPanel();
			JTextField columnName = new JTextField();
			JTextField columnValue = new JTextField();
			
			columnName.setText(columns.get(i));
			columnValue.setText(columnTypes.get(i).toString());
			column.add(columnName);
			column.add(columnValue);
			this.add(column);
		}
	}
	
	public List<String> getColumns() {
		ArrayList<String> columns = new ArrayList<String>();
		for(Component comp : this.getComponents()) {
			JPanel column = (JPanel) comp;
			columns.add(((JTextField) column.getComponents()[0]).getText());
		}
		
		return columns;
	}
	
	public List<ValueType> getColumnTypes() {
		ArrayList<ValueType> columnTypes = new ArrayList<ValueType>();
		for(Component comp : this.getComponents()) {
			JPanel column = (JPanel) comp;
			
			if(((JTextField) column.getComponents()[1]).getText().equals(ValueType.String.toString())) {
				columnTypes.add(ValueType.String);
			}
			else {
				columnTypes.add(ValueType.Number);
			}
		}
		
		return columnTypes;
	}
	
}