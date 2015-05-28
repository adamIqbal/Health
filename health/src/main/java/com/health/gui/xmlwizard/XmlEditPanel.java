package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.FileType;
import com.health.ValueType;
import com.health.input.InputDescriptor;
import com.health.input.InputException;

/**
 * Represents the wizard panel where one can specify the delimiters and columns
 * of the Config XML
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlEditPanel extends JPanel {
	private Path xml;
	private XmlStartEditPanel startPanel;
	private XmlColumnEditPanel columnPanel;

	// private JButton backButton;
	private JButton continueButton;

	public XmlEditPanel() {
		super();
		this.setLayout(new BorderLayout());

		startPanel = new XmlStartEditPanel();
		this.add(startPanel, BorderLayout.WEST);

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
	 * Models the input values as a {@link XmlConfigObject} and returns it
	 * 
	 * @return XmlConfigObject containing the input values
	 */
	public XmlConfigObject getValues() {
		XmlConfigObject config = new XmlConfigObject();
		
		config.setType(startPanel.getSelectedType());
		
		config.values = startPanel.getValues(config.type);

		config.setColumns(columnPanel.getColumns(),
				columnPanel.getColumnTypes());

		if (this.xml != null) {
			config.setPath(this.xml);
		}

		return config;
	}
	
	/**
	 * Loads current values of the selected XML file en sets the fields of the
	 * panel
	 * 
	 * @param xml
	 *            Path of XML file to edit
	 */
	public void setValues(Path xml) {
		try {
			InputDescriptor id = new InputDescriptor(xml.toString());
			
			//set values according to file format
			if(id.getFormat().equals("xlsx") || id.getFormat().equals("xls")) {
				//TODO XLS support
			}
			//else for now default to TXT format
			else {
				String[] values = {id.getStartDelimiter(), id.getEndDelimiter(),
						id.getDelimiter()};
				startPanel.setValues(values, FileType.TXT);
			}
			
			//set the columns
			columnPanel.setColumns(id.getColumns(), id.getColumnTypes());
			this.xml = xml;
		} catch (ParserConfigurationException | SAXException | IOException
				| InputException e) {
			System.out.println("Error loading: " + xml.toString());
		}
	}	
	
	/**
	 * Represents the panel one can edit the columns
	 * 
	 * @author Bjorn van der Laan
	 *
	 */
	private class XmlColumnEditPanel extends JPanel implements ActionListener {
		private JPanel columnPanel = new JPanel(new GridLayout(0, 1));
		private JPanel buttonPanel = new JPanel();
		private JButton addColumnButton = new JButton("Add extra column");
		private Dimension preferredDim;

		public XmlColumnEditPanel() {
			super();

			this.setLayout(new BorderLayout());

			preferredDim = new Dimension(100, 25);

			addColumnButton.addActionListener(this);
			buttonPanel.setPreferredSize(new Dimension(150, 50));
			buttonPanel.add(addColumnButton);

			this.add(new JScrollPane(columnPanel), BorderLayout.CENTER);
			this.add(buttonPanel, BorderLayout.SOUTH);
		}

		public void setColumns(List<String> columns, List<ValueType> columnTypes) {
			int size = columns.size();
			if (size != columnTypes.size()) {
				// Error! Lists not same size
			}

			for (int i = 0; i < size; i++) {
				JPanel column = new JPanel();
				JTextField columnName = new JTextField();
				JComboBox<ValueType> columnValue = new JComboBox<ValueType>(
						ValueType.values());

				columnName.setPreferredSize(preferredDim);
				columnValue.setPreferredSize(preferredDim);

				columnName.setText(columns.get(i));

				columnValue.setSelectedItem(columnTypes.get(i));

				column.add(columnName);
				column.add(columnValue);
				this.columnPanel.add(column);
			}
		}

		public List<String> getColumns() {
			ArrayList<String> columns = new ArrayList<String>();
			for (Component comp : this.columnPanel.getComponents()) {
				JPanel column = (JPanel) comp;
				columns.add(((JTextField) column.getComponents()[0]).getText());
			}

			return columns;
		}

		public List<ValueType> getColumnTypes() {			
			ArrayList<ValueType> columnTypes = new ArrayList<ValueType>();
			for (Component comp : this.columnPanel.getComponents()) {
				JPanel column = (JPanel) comp;

				if (((JComboBox<ValueType>) column.getComponents()[1])
						.getSelectedItem().equals(ValueType.String)) {
					columnTypes.add(ValueType.String);
				} else {
					columnTypes.add(ValueType.Number);
				}
			}

			return columnTypes;
		}

		public void addColumn() {
			JPanel panel = new JPanel();
			JTextField columnName = new JTextField();
			JComboBox<ValueType> columnValue = new JComboBox<ValueType>(
					ValueType.values());

			columnName.setPreferredSize(preferredDim);
			columnValue.setPreferredSize(preferredDim);
			
			columnName.setText("");

			panel.add(columnName);
			panel.add(columnValue);

			this.columnPanel.add(panel);
			this.columnPanel.repaint();
			this.columnPanel.revalidate();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			addColumn();
		}
	}
}