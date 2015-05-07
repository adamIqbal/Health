package com.health;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a collection of columns and records.
 *
 * @author Martijn
 */
public class Table extends Chunk implements Iterable<Chunk> {
    private Map<String, Column> columnMap;

    /**
     * Constructs a table with the given columns.
     *
     * @param columns
     *            a collection of columns that make up this table.
     * @throws NullPointerException
     *             if columns is null.
     * @throws IllegalArgumentException
     *             if columns contains a null element.
     */
    public Table(final Iterable<Column> columns) {
        Objects.requireNonNull(columns, "Argument columns cannot be null");

        this.columnMap = new HashMap<String, Column>();

        for (Column column : columns) {
            if (column == null) {
                throw new IllegalArgumentException(
                        "Argument columns olumns cannot not contain null references");
            }

            this.columnMap.put(column.getName(), column);
        }
    }

    /**
     * Returns an {@link Iterable} containing all columns in this table.
     *
     * @return the collection of columns of this table.
     */
    public final Iterable<Column> getColumns() {
        return this.columnMap.values();
    }

    /**
     * Returns the column with the given name.
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
}
