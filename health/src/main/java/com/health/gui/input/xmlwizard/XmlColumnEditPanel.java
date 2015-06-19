package com.health.gui.input.xmlwizard;

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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.health.ValueType;
import com.health.gui.VButton;

/**
 * Represents the panel one can edit the columns.
 * @author Bjorn van der Laan
 *
 */
public class XmlColumnEditPanel extends JPanel implements ActionListener {
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 8364710326816967217L;
    private final int buttonPanelWidth = 150;
    private final int buttonPanelHeight = 50;
    private JPanel columnPanel = new JPanel(new GridLayout(0, 1));
    private JPanel buttonPanel = new JPanel();
    private JButton addColumnButton = new JButton("Add extra column");
    private final Dimension preferredDim = new Dimension(100, 25);

    /**
     * Constructs an XmlColumnEditPanel object.
     */
    public XmlColumnEditPanel() {
        super();

        this.setLayout(new BorderLayout());

        addColumnButton.addActionListener(this);
        buttonPanel.setPreferredSize(new Dimension(buttonPanelWidth,
                buttonPanelHeight));
        buttonPanel.add(addColumnButton);

        this.add(new JScrollPane(columnPanel), BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        addColumn();
    }

    /**
     * Adds a column field and column type field to the panel.
     */
    public final void addColumn() {
        addColumn("", ValueType.Number);
    }

    /**
     * Adds a column field and column type field to the panel.
     * @param name
     *            name of column
     * @param type
     *            type of column
     */
    public final void addColumn(final String name, final ValueType type) {
        final int amountOfColumns = 3;
        JPanel panel = new JPanel(new GridLayout(1, amountOfColumns));
        JTextField columnName = new JTextField();
        columnName.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox<ValueType> columnValue = new JComboBox<ValueType>(
                ValueType.values());

        columnName.setPreferredSize(preferredDim);
        columnValue.setPreferredSize(preferredDim);
        columnValue.setSelectedItem(type);

        if (type == ValueType.Date) {
            columnName.setText(name + "[" + XmlWizard.getXml().getDateFormat()
                    + "]");
        } else {
            columnName.setText(name);
        }

        VButton deleteButton = new VButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                panel.getParent().remove(panel);
                columnPanel.repaint();
                columnPanel.revalidate();
            }
        });

        panel.add(columnName);
        panel.add(columnValue);
        panel.add(deleteButton);

        this.columnPanel.add(panel);
        this.columnPanel.repaint();
        this.columnPanel.revalidate();
    }

    /**
     * Clears the columns of the columnPanel.
     */
    public final void clearColumns() {
        columnPanel.removeAll();
    }

    /**
     * Gets the column names specified in the panel.
     * @return an array containing the names
     */
    public final List<String> getColumns() {
        ArrayList<String> columns = new ArrayList<String>();
        for (Component comp : this.columnPanel.getComponents()) {
            JPanel column = (JPanel) comp;
            columns.add(((JTextField) column.getComponents()[0]).getText());
        }

        return columns;
    }

    /**
     * Gets the column types specified in the panel.
     * @return an array containing the types
     */
    public final List<ValueType> getColumnTypes() {
        ArrayList<ValueType> columnTypes = new ArrayList<ValueType>();
        for (Component comp : this.columnPanel.getComponents()) {
            JPanel column = (JPanel) comp;
            ValueType type = (ValueType) ((JComboBox<ValueType>) column
                    .getComponents()[1]).getSelectedItem();

            if (type.equals(ValueType.String)) {
                columnTypes.add(ValueType.String);
            } else if (type.equals(ValueType.Number)) {
                columnTypes.add(ValueType.Number);
            } else {
                columnTypes.add(ValueType.Date);
            }
        }

        return columnTypes;
    }

    /**
     * Creates the column edit fields according to the selected config XML.
     * @param columns
     *            the column names of the config XML
     * @param columnTypes
     *            the column types of the config XML
     */
    public final void setColumns(final List<String> columns,
            final List<ValueType> columnTypes) {
        int size = columns.size();
        if (size != columnTypes.size()) {
            JOptionPane
                    .showMessageDialog(
                            new JFrame(),
                            "Amount of columns and amount of column types does not match.",
                            "Whoops!", JOptionPane.WARNING_MESSAGE);
        }

        clearColumns();
        for (int i = 0; i < size; i++) {
            addColumn(columns.get(i), columnTypes.get(i));
        }
    }
}
