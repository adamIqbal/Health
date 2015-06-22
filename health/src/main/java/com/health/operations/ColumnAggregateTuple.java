package com.health.operations;

/**
 * A tuple of containing the name of a column and an aggregate function. The
 * aggregate function is optional.
 */
public final class ColumnAggregateTuple {
    private final String column;
    private final AggregateFunction function;

    /**
     * Creates a new {@link ColumnAggregateTuple} with only the given column.
     *
     * @param column
     *            a column name.
     */
    public ColumnAggregateTuple(final String column) {
        this.column = column;
        this.function = null;
    }

    /**
     * Creates a new {@link ColumnAggregateTuple} with the given column and
     * aggregate function.
     *
     * @param column
     *            a column name.
     * @param function
     *            an aggregate function
     */
    public ColumnAggregateTuple(final String column,
            final AggregateFunction function) {
        this.column = column;
        this.function = function;
    }

    /**
     * Gets the name of the column in this tuple.
     *
     * @return the name of the column.
     */
    public String getColumn() {
        return this.column;
    }

    /**
     * Gets the modified name of the column to reflect that it contains the
     * result of an aggregate operation. The name remains the same if no
     * aggregate function exists in this tuple.
     *
     * @return the modified name of the column.
     */
    public String getAggregateColumn() {
        if (this.hasFunction()) {
            return this.function.getName() + "_" + this.getColumn();
        } else {
            return this.getColumn();
        }
    }

    /**
     * Gets whether or not this tuple contains an aggregate function.
     *
     * @return true if this tuple contains an aggregate function; otherwise
     *         false.
     */
    public boolean hasFunction() {
        return this.function != null;
    }

    /**
     * Gets the aggregate function in this tuple.
     *
     * @return the aggregate function.
     */
    public AggregateFunction getFunction() {
        return this.function;
    }
}
