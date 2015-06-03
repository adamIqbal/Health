package com.health.interpreter;

import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.health.AggregateFunctions;
import com.health.Table;
import com.health.operations.Chunk;
import com.health.script.MyScriptParser;
import com.health.script.runtime.Context;
import com.health.script.runtime.LValue;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.TableValue;
import com.health.script.runtime.Value;

public final class ChunkExpressionInterpreter {
    private ChunkExpressionInterpreter() {
    }

    public static Value interpret(final MyScriptParser.ChunkExpressionContext ctx, final Context context) {
        String tableIdent = ctx.tableIdent.getText();
        String columnIdent = ctx.columnIdent.getText();

        Table table = lookupTable(tableIdent, context);

        verifyHasColumn(table, tableIdent, columnIdent);

        Map<String, AggregateFunctions> aggregateFunctions =
                evaluateAggragateOperations(ctx.columnAggregateOperation());

        if (ctx.periodSpecifier() != null) {
            Period period = evaluatePeriod(ctx.periodSpecifier().period());

            return new TableValue(Chunk.chunkByTime(table, columnIdent, aggregateFunctions, period));
        } else {
            return new TableValue(Chunk.chunkByString(table, columnIdent, aggregateFunctions));
        }
    }

    private static Table lookupTable(final String tableName, final Context context) {
        LValue var = context.lookup(tableName);

        if (!TableValue.getStaticType().isAssignableFrom(var.getType())) {
            throw new ScriptRuntimeException("Chunking can only be performed on a table instance.");
        }

        return ((TableValue) var.get()).getValue();
    }

    private static void verifyHasColumn(final Table table, final String tableName, final String column) {
        if (table.getColumn(column) == null) {
            throw new ScriptRuntimeException(String.format("Table '%s' does not define a column named '%s'.",
                    tableName, column));
        }

    }

    private static Period evaluatePeriod(final MyScriptParser.PeriodContext ctx) {
        if (ctx.singularTimeUnit() != null) {
            return evaluateSingularPeriod(ctx);
        } else if (ctx.pluralTimeUnit() != null) {
            return evaluatePluralPeriod(ctx);
        } else {
            throw new RuntimeException();
        }
    }

    private static Period evaluateSingularPeriod(final MyScriptParser.PeriodContext ctx) {
        switch (ctx.singularTimeUnit().getText()) {
        case "day":
            return Period.ofDays(1);
        case "week":
            return Period.ofWeeks(1);
        case "month":
            return Period.ofMonths(1);
        case "year":
            return Period.ofYears(1);
        default:
            throw new ScriptRuntimeException("");
        }
    }

    private static Period evaluatePluralPeriod(final MyScriptParser.PeriodContext ctx) {
        int number = (int) Double.parseDouble(ctx.NUMBER().getText());

        switch (ctx.pluralTimeUnit().getText()) {
        case "days":
            return Period.ofDays(number);
        case "weeks":
            return Period.ofWeeks(number);
        case "months":
            return Period.ofMonths(number);
        case "years":
            return Period.ofYears(number);
        default:
            throw new ScriptRuntimeException("");
        }
    }

    private static Map<String, AggregateFunctions> evaluateAggragateOperations(
            final List<MyScriptParser.ColumnAggregateOperationContext> columnAggregateOperation) {
        Map<String, AggregateFunctions> aggregateFunctions = new HashMap<String, AggregateFunctions>();

        for (MyScriptParser.ColumnAggregateOperationContext ctx : columnAggregateOperation) {
            aggregateFunctions.put(ctx.IDENTIFIER().getText(), evaluateAggragateOperation(ctx.aggregateOperation()));
        }

        return aggregateFunctions;
    }

    private static AggregateFunctions evaluateAggragateOperation(final MyScriptParser.AggregateOperationContext ctx) {
        switch (ctx.getText()) {
        case "average":
            return AggregateFunctions.Average;
        case "sum":
            return AggregateFunctions.Sum;
        case "min":
            return AggregateFunctions.Min;
        case "max":
            return AggregateFunctions.Max;
        default:
            throw new ScriptRuntimeException(String.format("Undefined aggregate function '%s'.", ctx.getText()));
        }
    }
}
