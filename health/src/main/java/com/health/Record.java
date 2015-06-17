package com.health;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
    public List<Object> getValues() {
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
        return this.getValue(table.getColumn(name), null);
    }

    /**
     * Gets the value of the column with the given index.
     *
     * @param index
     *            the index of the column whose value to get.
     * @return the value of the column with the given index if found; otherwise
     *         null.
     */
    public Object getValue(final int index) {
        return this.getValue(table.getColumn(index), null);
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
        Object value = this.getValue(table.getColumn(name), ValueType.Number);

        if (value == null) {
            value = (double) 0;
        }

        return (Double) value;
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
        return (String) this.getValue(table.getColumn(name), ValueType.String);
    }

    /**
     * Gets the {@link LocalDateTime} value of the column with the given name.
     *
     * @param name
     *            the name of the column whose value to get.
     * @return the value of the column with the given name if found; otherwise
     *         null.
     * @throws IllegalStateException
     *             if the specified column does not contain String values.
     */
    public LocalDateTime getDateValue(final String name) {
        // Retrieve the value for a number and cast it to String
        return (LocalDateTime) this.getValue(table.getColumn(name), ValueType.Date);
    }

    /**
     * Sets the value of the column with the given index.
     *
     * @param index
     *            the index of the column whose value to set.
     * @param value
     *            the new value.
     * @throws IllegalArgumentException
     *             if a column with the given name was not found.
     * @throws IllegalStateException
     *             if the specified column does not contain values of the given
     *             type.
     */
    public void setValue(final int index, final Object value) {
        this.setValue(table.getColumn(index), value, getValueType(value));
    }

    /**
     * Sets the value of the column with the given name.
     *
     * @param name
     *            the name of the column whose value to set.
     * @param value
     *            the new value.
     * @throws IllegalArgumentException
     *             if a column with the given name was not found.
     * @throws IllegalStateException
     *             if the specified column does not contain values of the given
     *             type.
     */
    public void setValue(final String name, final Object value) {
        this.setValue(table.getColumn(name), value, getValueType(value));
    }

    private static ValueType getValueType(final Object value) {
        if (value instanceof Double) {
            return ValueType.Number;
        } else if (value instanceof String) {
            return ValueType.String;
        } else if (value instanceof LocalDateTime) {
            return ValueType.Date;
        } else if (value == null) {
            return null;
        } else {
            throw new IllegalArgumentException("Unsupported value type, must be either Double, String or LocalDate.");
        }
    }

    /**
     * Adds a copy of this record to the given table. The table must have
     * identical columns to the table that this record belongs to.
     *
     * @param table
     *            the table to copy the record to.
     */
    public void copyTo(final Table table) {
        Objects.requireNonNull(table);

        List<Column> columns1 = this.table.getColumns();
        List<Column> columns2 = table.getColumns();

        if (columns1.size() != columns2.size()) {
            throw new IllegalArgumentException(
                    "The given table must have the same columns as the table that this record belongs to.");
        }

        verifyColumnTypesEqual(columns1, columns2);

        Record copy = new Record(table);
        System.arraycopy(this.values, 0, copy.values, 0, columns1.size());
    }

    private static void verifyColumnTypesEqual(final List<Column> columns1, final List<Column> columns2) {
        for (int i = 0; i < columns1.size(); i++) {
            ValueType type1 = columns1.get(i).getType();
            ValueType type2 = columns2.get(i).getType();

            if (type1 != type2) {
                throw new IllegalArgumentException(
                        "The given table must have the same columns as the table that this record belongs to.");
            }
        }
    }

    private Object getValue(final Column column, final ValueType type) {
        // Return null if a column with the given name or index was not found
        if (column == null) {
            return null;
        }

        // Throw an exception if the column contains a type does not match the
        // given type
        if (type != null && column.getType() != type) {
            throw new IllegalStateException();
        }

        return this.values[column.getIndex()];
    }

    private void setValue(final Column column, final Object value, final ValueType type) {
        // Throw an exception if a column with the given name or index was not
        // found
        if (column == null) {
            throw new IllegalArgumentException();
        }

        // Throw an exception if the column contains a type does not match the
        // given type
        if (type != null && column.getType() != type) {
            throw new IllegalStateException();
        }

        this.values[column.getIndex()] = value;
    }
}
