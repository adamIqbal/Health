package com.health.operations;

/**
 * A class which preforms all aggregate functions.
 *
 * @author daan
 *
 */
public final class AggregateFunctions {
    private AggregateFunctions() {
    }

    public static AggregateFunction average() {
        return new AggregateFunction("average", (data) -> {
            double sum = sum().apply(data);
            double average = (double) sum / data.length;

            return average;
        });
    }

    public static AggregateFunction sum() {
        return new AggregateFunction("sum", (data) -> {
            double sum = 0;

            for (int i = 0; i < data.length; i++) {
                sum = sum + data[i];
            }

            return sum;
        });
    }

    public static AggregateFunction min() {
        return new AggregateFunction("min", (data) -> {
            double min = data[0];

            for (int i = 0; i < data.length; i++) {
                if (data[i] < min) {
                    min = data[i];
                }
            }

            return min;
        });
    }

    public static AggregateFunction max() {
        return new AggregateFunction("max", (data) -> {
            double max = data[0];

            for (int i = 0; i < data.length; i++) {
                if (data[i] > max) {
                    max = data[i];
                }
            }

            return max;
        });
    }
}
