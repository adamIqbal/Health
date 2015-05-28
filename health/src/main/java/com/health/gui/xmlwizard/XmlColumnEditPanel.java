package com.health.gui.xmlwizard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.health.ValueType;

/**
 * Represents the panel one can edit the columns
 * 
 * @author Bjorn van der Laan
 *
 */
public class XmlColumnEditPanel extends JPanel implements ActionListener {
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