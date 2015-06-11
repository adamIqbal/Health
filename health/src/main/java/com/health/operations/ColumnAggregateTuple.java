package com.health.operations;

public final class ColumnAggregateTuple {
    private final String column;
    private final AggregateFunction function;

    public ColumnAggregateTuple(final String column) {
        this.column = column;
        this.function = null;
    }

    public ColumnAggregateTuple(final String column, final AggregateFunction function) {
        this.column = column;
        this.function = function;
    }

    public String getColumn() {
        return this.column;
    }

    public String getAggregateColumn() {
        if (this.hasFunction()) {
            return this.function.getName() + "_" + this.getColumn();
        } else {
            return this.getColumn();
        }
    }

    public boolean hasFunction() {
        return this.function != null;
    }

    public AggregateFunction getFunction() {
        return this.function;
    }
}
