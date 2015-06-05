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
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.TableValue;
import com.health.script.runtime.Value;

public final class ChunkExpressionInterpreter extends TableExpressionInterpreter {
    protected ChunkExpressionInterpreter(
            final Context context,
            final ExpressionValueVisitor expressionVisitor) {
        super(context, expressionVisitor);
    }

    public Value interpret(final MyScriptParser.ChunkExpressionContext ctx) {
        String tableIdent = ctx.table.getText();
        String columnIdent = ctx.column.getText();

        Table table = this.lookupTable(tableIdent);

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
