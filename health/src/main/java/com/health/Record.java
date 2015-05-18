package com.health;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;

/**
 * Represents a row of values in a {@link Table}.
 *
 * @author Martijn
 */
public final class Record {
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
    public Record(final Table table) {
        Objects.requireNonNull(table, "Argument table cannot be null.");

        this.table = table;
        this.values = new Object[table.getColumns().size()];

        table.addRecord(this);
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
    public Object getValue(final String name) {
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
    public Double getNumberValue(final String name) {
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
    public String getStringValue(final String name) {
        // Retrieve the value for a number and cast it to String
        return (String) this.getValue(name, EnumSet.of(ValueType.String));
    }

    /**
     * Sets the {@link Double} value of the column with the given index.
     *
     * @param index
     *            the index of the column whose value to set.
     * @param value
     *            the new value.
     * @throws IllegalArgumentException
     *             if a column with the given name was not found.
     * @throws IllegalStateException
     *             if the specified column does not contain Double values.
     */
    public void setValue(final int index, final Double value) {
        this.setValue(this.table.getColumn(index).getName(), value);
    }

    /**
     * Sets the {@link String} value of the column with the given index.
     *
     * @param index
     *            the index of the column whose value to set.
     * @param value
     *            the new value.
     * @throws IllegalArgumentException
     *             if a column with the given name was not found.
     * @throws IllegalStateException
     *             if the specified column does not contain String values.
     */
    public void setValue(final int index, final String value) {
        this.setValue(this.table.getColumn(index).getName(), value);
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
    public void setValue(final String name, final Double value) {
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
    public void setValue(final String name, final String value) {
        this.setValue(name, value, ValueType.String);
    }

    private Object getValue(final String name, final EnumSet<ValueType> types) {
        assert types != null;

        Column column = this.table.getColumn(name);

        // Return null if a column with the given name was not found
        if (column == null) {
            return null;
        }

        // Throw an exception if the column contains a type not contained in the
        // enum set
        if (!types.contains(column.getType())) {
            throw new IllegalStateException();
        }

        return this.values[column.getIndex()];
    }

    private void setValue(
            final String name,
            final Object value,
            final ValueType type) {
        assert type != null;

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
