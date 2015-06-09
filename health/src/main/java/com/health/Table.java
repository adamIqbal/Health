package com.health;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a collection of {@link Column}s and {@link Record}s.
 *
 * @author Martijn
 */
public final class Table implements Iterable<Record> {
    private List<Column> columns;
    private Map<String, Column> columnMap;
    private List<Record> records;

    /**
     * Constructs a table with the given columns. Each column must have a unique
     * index in the range [0, n).
     *
     * @param columns
     *            a collection of columns that make up this table. The order of
     *            the columns does not matter.
     * @throws NullPointerException
     *             if columns is null.
     * @throws IllegalArgumentException
     *             if columns contains a null element.
     */
    public Table(final Iterable<Column> columns) {
        Objects.requireNonNull(columns, "Argument columns cannot be null.");

        Table.verifyColumnIndices(columns);

        this.columns = new ArrayList<Column>();
        this.columnMap = new HashMap<String, Column>();
        this.records = new ArrayList<Record>();

        for (Column column : columns) {
            this.columns.add(column);
            this.columnMap.put(column.getName(), column);
        }

        // Sort the columns by index and make the list read-only
        this.columns.sort((a, b) -> Integer.compare(a.getIndex(), b.getIndex()));
        this.columns = Collections.unmodifiableList(this.columns);
    }

    /**
     * Gets an {@link List} containing all columns in this table.
     *
     * @return an {@link List} containing all columns of this table.
     */
    public List<Column> getColumns() {
        return this.columns;
    }

    /**
     * Gets the column with the given index.
     *
     * @param index
     *            the index of the column to get.
     * @return the column with the given index.
     * @throws IndexOutOfBoundsException
     *             if the index is out of range.
     */
    public Column getColumn(final int index) {
        return this.columns.get(index);
    }

    /**
     * Gets the column with the given name.
     *
     * @param name
     *            the name of the column to get.
     * @return the column with the given name if found; otherwise null.
     */
    public Column getColumn(final String name) {
        return this.columnMap.get(name);
    }

    /**
     * Adds the given record to this table.
     *
     * @param record
     *            the record to add.
     * @throws NullPointerException
     *             if record is null.
     * @throws IllegalArgumentException
     *             if record belongs to a different table.
     */
    public void addRecord(final Record record) {
        Objects.requireNonNull(record, "Argument record cannot be null.");

        if (record.getTable() != this) {
            throw new IllegalArgumentException(
                    "Argument record already belongs to a different table.");
        }

        this.records.add(record);
    }

    /**
     * Removes the first occurrence of the given record from this table if
     * present.
     *
     * @param record
     *            the record to remove.
     */
    public void removeRecord(final Record record) {
        this.records.remove(record);
    }

    /**
     * Returns an {@link List} containing all records in this table.
     *
     * @return an {@link List} containing all records in this table.
     */
    public List<Record> getRecords() {
        return Collections.unmodifiableList(this.records);
    }

    /**
     * Groups the records of this table on the given key, and projects the
     * groups onto a table identical to the current table using the given result
     * selector.
     *
     * @param keyColumn
     *            the key on which to group.
     * @param resultSelector
     *            a function that projects a group onto a record.
     * @return the table containing the grouped records.
     */
    public Table groupBy(
            final String keyColumn,
            final TriFunction<Object, Table, Record> resultSelector) {
        return this.groupBy(keyColumn, new Table(this.columns), resultSelector);
    }

    /**
     * Groups the records of this table on the given key, and projects the
     * groups onto a given table using the given result selector.
     *
     * @param keyColumn
     *            the key on which to group.
     * @param resultTable
     *            the table that will contain the grouped records.
     * @param resultSelector
     *            a function that projects a group onto a record.
     * @return the table containing the grouped records.
     */
    public Table groupBy(
            final String keyColumn,
            final Table resultTable,
            final TriFunction<Object, Table, Record> resultSelector) {
        if (this.getColumn(keyColumn) == null) {
            throw new IllegalArgumentException(String.format(
                    "The table does not contain a column named '%s'.",
                    keyColumn));
        }

        Map<Object, Table> groups = new HashMap<Object, Table>();

        // Group all the records by their key
        for (Record record : this.records) {
            Object key = record.getValue(keyColumn);
            Table group = groups.get(key);

            if (group == null) {
                group = new Table(this.columns);
                groups.put(key, group);
            }

            record.copyTo(group);
        }

        // Convert each group to a single record
        for (Entry<Object, Table> entry : groups.entrySet()) {
            Record result = new Record(resultTable);

            resultSelector.apply(entry.getKey(), entry.getValue(), result);
        }

        return resultTable;
    }

    /**
     * Returns a chunk iterator that can be used to iterate over this table.
     *
     * @return a chunk iterator that can be used to iterate over this table.
     */
    @Override
    public Iterator<Record> iterator() {
        return this.records.iterator();
    }

    private static void verifyColumnIndices(final Iterable<Column> columns) {
        assert columns != null;

        Set<Integer> indices = new HashSet<Integer>();
        int count = 0;
        int minIndex = Integer.MAX_VALUE;
        int maxIndex = Integer.MIN_VALUE;

        for (Column column : columns) {
            // Throw an exception if the column is a null reference
            if (column == null) {
                throw new IllegalArgumentException(
                        "Argument columns cannot not contain null references.");
            }

            int index = column.getIndex();

            // Throw an exception if the index is not unique
            if (!indices.add(index)) {
                throw new IllegalArgumentException(
                        "Each column must have a unique index.");
            }

            // Find the smallest and largest index
            minIndex = Math.min(minIndex, index);
            maxIndex = Math.max(maxIndex, index);

            count++;
        }

        // Throw an exception if columns is empty
        if (count == 0) {
            throw new IllegalArgumentException(
                    "Argument columns must contain at least one column.");
        }

        // Throw an exception if the indices do not start at 0 or end at n-1
        if (minIndex != 0 || maxIndex != count - 1) {
            throw new IllegalArgumentException(
                    "The indices of the columns must be in the range [0, n).");
        }
    }
}
