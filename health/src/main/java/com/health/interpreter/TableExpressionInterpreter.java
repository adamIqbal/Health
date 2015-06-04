package com.health.interpreter;

import com.health.Table;
import com.health.script.runtime.Context;
import com.health.script.runtime.LValue;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.TableValue;

public abstract class TableExpressionInterpreter {
    protected static Table lookupTable(final String tableName, final Context context) {
        LValue var = context.lookup(tableName);

        if (!TableValue.getStaticType().isAssignableFrom(var.getType())) {
            throw new ScriptRuntimeException("Chunking can only be performed on a table instance.");
        }

        return ((TableValue) var.get()).getValue();
    }

    protected static void verifyHasColumn(final Table table, final String tableName, final String column) {
        if (table.getColumn(column) == null) {
            throw new ScriptRuntimeException(String.format("Table '%s' does not define a column named '%s'.",
                    tableName, column));
        }
    }
}
