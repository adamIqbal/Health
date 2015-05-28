package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.FileType;
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

			// set values according to file format
			if (id.getFormat().equals("xlsx") || id.getFormat().equals("xls")) {
				// TODO XLS support
			}
			// else for now default to TXT format
			else {
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