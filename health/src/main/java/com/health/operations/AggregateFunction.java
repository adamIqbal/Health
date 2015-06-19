package com.health.operations;

import java.util.List;
import java.util.function.BiFunction;

import com.health.Record;

/**
 * Represents a named aggregate function.
 * @param <T>
 *            the type
 */
public final class AggregateFunction<T> {
    private final String name;
    private final BiFunction<List<Record>, String, Double> function;

    /**
     * Creates a new instance of {@link AggregateFunction} with the given name
     * and function.
     *
     * @param name
     *            the name of the aggregate function.
     * @param function
     *            the actual aggregation function.
     */
    public AggregateFunction(final String name,
            final BiFunction<List<Record>, String, Double> function) {
        this.name = name;
        this.function = function;
    }

    /**
     * Gets the name of the aggregate function.
     *
     * @return the name of the aggregate function.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Applies the aggregate function to the given data and returns the result.
     *
     * @param records
     *            the records to apply the aggregate function on.
     * @param column
     *            the column to apply the aggregate function on.
     * @return the result of the aggregate function on the given data.
     */
    public double apply(final List<Record> records, final String column) {
        return function.apply(records, column);
    }
}
