package com.health.operations;

import java.util.function.Function;

import com.health.Record;
import com.health.Table;

public final class Constraints {
    private Constraints() {
    }

    /**
     * Creates a new table containing only the records from the given table for
     * which the given predicate is evaluated as true.
     *
     * @param predicate
     *            a predicate that determines whether or not to add a given
     *            record to the new table.
     * @param table
     *            the table to constrain on.
     * @return a new table containing only the records for which the given
     *         predicate is evaluated as true.
     */
    public static Table constrain(final Function<Record, Boolean> predicate, final Table table) {
        Table constrained = new Table(table.getColumns());

        for (Record record : table.getRecords()) {
            if (predicate.apply(record)) {
                record.copyTo(constrained);
            }
        }

        return constrained;
    }
}
