package com.health.operations;

import java.util.function.Function;

/**
 * Represents a named aggregate function.
 */
public final class AggregateFunction {
    private final String name;
    private final Function<double[], Double> function;

    /**
     * Creates a new instance of {@link AggregateFunction} with the given name
     * and function.
     *
     * @param name
     *            the name of the aggregate function.
     * @param function
     *            the actual aggregation function.
     */
    public AggregateFunction(final String name, final Function<double[], Double> function) {
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
     * @param data
     *            the data to apply the aggregate function on.
     * @return the result of the aggregate function on the given data.
     */
    public double apply(final double[] data) {
        return function.apply(data);
    }
}
