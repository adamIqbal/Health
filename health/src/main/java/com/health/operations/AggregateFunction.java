package com.health.operations;

import java.util.function.Function;

public final class AggregateFunction {
    private final String name;
    private final Function<double[], Double> function;

    public AggregateFunction(final String name, final Function<double[], Double> function) {
        this.name = name;
        this.function = function;
    }

    public String getName() {
        return this.name;
    }

    public double apply(final double[] data) {
        return function.apply(data);
    }
}
