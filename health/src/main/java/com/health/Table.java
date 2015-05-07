package com.health;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a collection of {@link Column}s and {@link Record}s.
 *
 * @author Martijn
 */
public class Table extends Chunk implements Iterable<Chunk> {
    private Map<String, Column> columnMap;

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
        Objects.requireNonNull(columns, "Argument columns cannot be null");

        Table.verifyColumnIndices(columns);

        this.columnMap = new HashMap<String, Column>();

        for (Column column : columns) {
            this.columnMap.put(column.getName(), column);
        }
    }

    /**
     * Gets an {@link Iterable} containing all columns in this table.
     *
     * @return an {@link Iterable} containing all columns of this table.
     */
    public final Collection<Column> getColumns() {
        return Collections.unmodifiableCollection(this.columnMap.values());
    }

    /**
     * Gets the column with the given name.
     *
     * @param name
     *            the name of the column to get.
     * @return the column with the given name if found; otherwise null.
     */
    public final Column getColumn(final String name) {
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
    @Override
    public void addRecord(Record record) {
        Objects.requireNonNull(record, "Argument record cannot be null");

        if (record.getTable() != this) {
            throw new IllegalArgumentException(
                    "Argument record already belongs to a different table");
        }

        super.addRecord(record);
    }

    /**
     * Returns a chunk iterator that can be used to iterate over this table.
     *
     * @return a chunk iterator that can be used to iterate over this table.
     */
    @Override
    public final Iterator<Chunk> iterator() {
        return Arrays.asList((Chunk) this).iterator();
    }

    private static void verifyColumnIndices(Iterable<Column> columns) {
        Set<Integer> indices = new HashSet<Integer>();
        int count = 0;
        int minIndex = Integer.MAX_VALUE;
        int maxIndex = Integer.MIN_VALUE;

        for (Column column : columns) {
            // Throw an exception if the column is a null reference
            if (column == null) {
                throw new IllegalArgumentException(
                        "Argument columns cannot not contain null references");
            }

            int index = column.getIndex();

            // Throw an exception if the index is not unique
            if (!indices.add(index)) {
                throw new IllegalArgumentException(
                        "Each column must have a unique index");
            }

            // Find the smallest and largest index
            minIndex = index < minIndex ? index : minIndex;
            maxIndex = index > maxIndex ? index : maxIndex;

            count++;
        }

        // Throw an exception if columns is empty
        if (count == 0) {
            throw new IllegalArgumentException(
                    "Argument columns must contain at least one column");
        }

        // Throw an exception if the indices do not start at 0 or end at n-1
        if (minIndex != 0 || maxIndex != count - 1) {
            throw new IllegalArgumentException(
                    "The indices of the columns must be in the range [0, n)");
        }
    }
}
