package com.health.interpreter;

import com.health.Table;
import com.health.script.runtime.Context;
import com.health.script.runtime.LValue;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.WrapperValue;

/**
 * A base class with common functions for interpreter of table expressions.
 */
public abstract class TableExpressionInterpreter {
    private final Context context;
    private final ExpressionValueVisitor expressionVisitor;

    /**
     * Creates a {@link TableExpressionInterpreter} with the given context and
     * expressionVisitor.
     *
     * @param context
     *            the context.
     * @param expressionVisitor
     *            the expressionVisitor.
     */
    protected TableExpressionInterpreter(
            final Context context,
            final ExpressionValueVisitor expressionVisitor) {
        this.context = context;
        this.expressionVisitor = expressionVisitor;
    }

    /**
     * Looks up a table from the context or throws an exception if the variable
     * is undefined or not a table.
     *
     * @param tableName
     *            the name of the table to retrieve.
     * @return the table declared with the given name.
     */
    protected final Table lookupTable(final String tableName) {
        LValue var = this.context.lookup(tableName);

        if (!WrapperValue.getWrapperType(Table.class).isAssignableFrom(var.getType())) {
            throw new ScriptRuntimeException("Chunking can only be performed on a table instance.");
        }

        return ((WrapperValue<Table>) var.get()).getValue();
    }

    /**
     * Throws an exception if the given table does not define a column with the
     * given name.
     *
     * @param table
     *            the table to be verified.
     * @param tableName
     *            the name of the table.
     * @param column
     *            the column to be verified.
     */
    protected static final void verifyHasColumn(final Table table, final String tableName, final String column) {
        if (table.getColumn(column) == null) {
            throw new ScriptRuntimeException(String.format("Table '%s' does not define a column named '%s'.",
                    tableName, column));
        }
    }

    /**
     * Gets the context.
     *
     * @return the context.
     */
    protected final Context getContext() {
        return this.context;
    }

    /**
     * Gets the expression visitor.
     *
     * @return the expression visitor.
     */
    protected final ExpressionValueVisitor getExpressionVisitor() {
        return this.expressionVisitor;
    }
}
