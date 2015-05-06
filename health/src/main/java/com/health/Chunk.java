package com.health;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a group of @see {@link Record}s.
 *
 * @author Martijn
 */
public class Chunk {
    private Collection<Record> records;

    /**
     * Constructs an empty chunk.
     */
    public Chunk() {
        this.records = new ArrayList<Record>();
    }

    /**
     * Adds the given record to this chunk.
     *
     * @param record
     *            the record to add.
     * @throws NullPointerException
     *             if record is null.
     */
    public void addRecord(Record record) {
        Objects.requireNonNull(record, "Argument record cannot be null");

        this.records.add(record);
    }

    /**
     * Removes the first occurrence of the given record from this chunk if
     * present.
     *
     * @param record
     *            the record to remove.
     */
    public void removeRecord(Record record) {
        this.records.remove(record);
    }

    /**
     * Returns an {@link Iterable} containing all records in this chunk.
     *
     * @return an {@link Iterable} containing all records in this chunk.
     */
    public Iterable<Record> getRecords() {
        return this.records;
    }
}
