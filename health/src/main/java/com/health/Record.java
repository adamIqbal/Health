package com.health;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;

/**
 * Represents a row of values in a {@link Table}.
 *
 * @author Martijn
 */
public class Record {
    private Table table;
    private Object[] values;

    /**
     * Constructs a {@link Record} that belongs to the given table.
     *
     * @param table
     *            the table this record belongs to.
     * @throws NullPointerException
     *             if table is null.
     */
    public Record(Table table) {
        Objects.requireNonNull(table, "Argument table cannot be null");

        this.table = table;
        this.values = new Object[table.getColumns().size()];
    }

    /**
     * Gets the table this record belongs to.
     * 
     * @return the table this record belongs to.
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Gets an iterable containing the values in this record.
     * 
     * @return an iterable containing the values in this record.
     */
    public Iterable<Object> getValues() {
        return Arrays.asList(this.values);
    }

    /**
     * Gets the value of the column with the given name.
     *
     * @param name
     *            the name of the column whose value to get.
     * @return the value of the column with the given name if found; otherwise
     *         null.
     */
    public Object getValue(String name) {
        // Retrieve the value for any of the supported types
        return this.getValue(name, EnumSet.allOf(ValueType.class));
    }

    /**
     * Gets the {@link Double} value of the column with the given name.
     *
     * @param name
     *            the name of the column whose value to get.
     * @return the value of the column with the given name if found; otherwise
     *         null.
     * @throws IllegalStateException
     *             if the specified column does not contain Double values.
     */
    public Double getNumberValue(String name) {
        // Retrieve the value for a number and cast it to Double
        return (Double) this.getValue(name, EnumSet.of(ValueType.Number));
    }

    /**
     * Gets the {@link String} value of the column with the given name.
     *
     * @param name
     *            the name of the column whose value to get.
     * @return the value of the column with the given name if found; otherwise
     *         null.
     * @throws IllegalStateException
     *             if the specified column does not contain String values.
     */
    public String getStringValue(String name) {
        // Retrieve the value for a number and cast it to String
        return (String) this.getValue(name, EnumSet.of(ValueType.String));
    }

    /**
     * Sets the {@link Double} value of the column with the given name.
     *
     * @param name
     *            the name of the column whose value to set.
     * @param value
     *            the new value.
     * @throws IllegalArgumentException
     *             if a column with the given name was not found.
     * @throws IllegalStateException
     *             if the specified column does not contain Double values.
     */
    public void setValue(String name, Double value) {
        this.setValue(name, value, ValueType.Number);
    }

    /**
     * Sets the {@link String} value of the column with the given name.
     *
     * @param name
     *            the name of the column whose value to set.
     * @param value
     *            the new value.
     * @throws IllegalArgumentException
     *             if a column with the given name was not found.
     * @throws IllegalStateException
     *             if the specified column does not contain String values.
     */
    public void setValue(String name, String value) {
        this.setValue(name, value, ValueType.String);
    }

    private Object getValue(String name, EnumSet<ValueType> types) {
        Column column = this.table.getColumn(name);

        // Return null if a column with the given name was not found
        if (column == null) {
            return null;
        }

        // Throw an exception if the column contains a type not contained in the
        // num set
        if (!types.contains(column.getType())) {
            throw new IllegalStateException();
        }

        return this.values[column.getIndex()];
    }

    private void setValue(String name, Object value, ValueType type) {
        Column column = this.table.getColumn(name);

        // Throw an exception if a column with the given name was not found
        if (column == null) {
            throw new IllegalArgumentException();
        }

        // Throw an exception if the column contains a type does not match the
        // given type
        if (column.getType() != type) {
            throw new IllegalStateException();
        }

        this.values[column.getIndex()] = value;
    }
}
