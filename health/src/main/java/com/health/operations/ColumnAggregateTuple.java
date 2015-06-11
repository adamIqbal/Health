package com.health.operations;

public final class ColumnAggregateTuple {
    private final String column;
    private final AggregateFunction function;

    public ColumnAggregateTuple(final String column, final AggregateFunction function) {
        this.column = column;
        this.function = function;
    }

    public String getColumn() {
        return this.column;
    }

    public AggregateFunction getFunction() {
        return this.function;
    }
}
