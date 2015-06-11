package com.health.operations;

/**
 * A class which provides aggregate functions.
 *
 * @author daan
 */
public final class AggregateFunctions {
    private AggregateFunctions() {
    }

    /**
     * Returns an aggregate function that counts the number of elements.
     *
     * @return an aggregate function.
     */
    public static AggregateFunction count() {
        return new AggregateFunction("count", (data) -> {
            return (double) data.length;
        });
    }

    /**
     * Returns an aggregate function that calculates the average of the given
     * elements.
     *
     * @return an aggregate function.
     */
    public static AggregateFunction average() {
        return new AggregateFunction("average", (data) -> {
            double sum = sum().apply(data);
            double average = (double) sum / data.length;

            return average;
        });
    }

    /**
     * Returns an aggregate function that calculates the sum of the given
     * elements.
     *
     * @return an aggregate function.
     */
    public static AggregateFunction sum() {
        return new AggregateFunction("sum", (data) -> {
            double sum = 0;

            for (int i = 0; i < data.length; i++) {
                sum = sum + data[i];
            }

            return sum;
        });
    }

    /**
     * Returns an aggregate function that calculates the minimum of the given
     * elements.
     *
     * @return an aggregate function.
     */
    public static AggregateFunction min() {
        return new AggregateFunction("min", (data) -> {
            double min = Double.POSITIVE_INFINITY;

            for (int i = 0; i < data.length; i++) {
                data[i] = Math.min(min, data[i]);
            }

            return min;
        });
    }

    /**
     * Returns an aggregate function that calculates the maximum of the given
     * elements.
     *
     * @return an aggregate function.
     */
    public static AggregateFunction max() {
        return new AggregateFunction("max", (data) -> {
            double max = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < data.length; i++) {
                data[i] = Math.max(max, data[i]);
            }

            return max;
        });
    }
}
