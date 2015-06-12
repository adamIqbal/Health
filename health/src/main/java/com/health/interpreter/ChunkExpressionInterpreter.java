package com.health.interpreter;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.health.Table;
import com.health.operations.AggregateFunction;
import com.health.operations.AggregateFunctions;
import com.health.operations.Chunk;
import com.health.operations.ColumnAggregateTuple;
import com.health.script.MyScriptParser;
import com.health.script.MyScriptParser.ChunkSelectionListContext;
import com.health.script.runtime.Context;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.TableValue;
import com.health.script.runtime.Value;

/**
 * Represents an interpreter for chunking expressions.
 */
public final class ChunkExpressionInterpreter extends TableExpressionInterpreter {
    /**
     * Creates a new instance of {@link ChunkExpressionInterpreter} with the
     * given context and expressionVisitor.
     *
     * @param context
     *            the context.
     * @param expressionVisitor
     *            the expressionVisitor.
     */
    protected ChunkExpressionInterpreter(
            final Context context,
            final ExpressionValueVisitor expressionVisitor) {
        super(context, expressionVisitor);
    }

    /**
     * Interprets the given ChunkExpressionContext and returns the resulting
     * value.
     *
     * @param ctx
     *            the chunk expression context.
     * @return the value resulting from evaluating the expression.
     */
    public Value interpret(final MyScriptParser.ChunkExpressionContext ctx) {
        String tableIdent = ctx.table.getText();
        String columnIdent = ctx.column.getText();

        Table table = this.lookupTable(tableIdent);

        verifyHasColumn(table, tableIdent, columnIdent);

        List<ColumnAggregateTuple> aggregateFunctions =
                evaluateChunkSelectionList(ctx.chunkSelectionList());

        if (ctx.periodSpecifier() != null) {
            Period period = evaluatePeriod(ctx.periodSpecifier().period());

            return new TableValue(Chunk.chunkByPeriod(table, columnIdent, aggregateFunctions, period));
        } else {
            return new TableValue(Chunk.chunkByColumn(table, columnIdent, aggregateFunctions));
        }
    }

    private List<ColumnAggregateTuple> evaluateChunkSelectionList(final ChunkSelectionListContext ctx) {
        List<ColumnAggregateTuple> aggregateFunctions = new ArrayList<ColumnAggregateTuple>();

        evaluateChunkSelectionList(ctx, aggregateFunctions);

        return aggregateFunctions;
    }

    private void evaluateChunkSelectionList(
            final ChunkSelectionListContext ctx,
            final List<ColumnAggregateTuple> aggregateFunctions) {
        if (ctx == null) {
            return;
        }

        evaluateChunkSelectionList(ctx.chunkSelectionList(), aggregateFunctions);

        aggregateFunctions.add(evaluateColumnOrAggragateOperation(ctx.columnOrAggregateOperation()));
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
            throw new ScriptRuntimeException("Undefined time period '" + ctx.singularTimeUnit().getText() + "'.");
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
            throw new ScriptRuntimeException("Undefined time period '" + ctx.singularTimeUnit().getText() + "'.");
        }
    }

    private static ColumnAggregateTuple evaluateColumnOrAggragateOperation(
            final MyScriptParser.ColumnOrAggregateOperationContext ctx) {

        if (ctx.aggregateOperation() == null) {
            return new ColumnAggregateTuple(ctx.IDENTIFIER().getText());
        } else {
            return new ColumnAggregateTuple(
                    ctx.IDENTIFIER().getText(),
                    evaluateAggragateOperation(ctx.aggregateOperation()));
        }
    }

    private static AggregateFunction evaluateAggragateOperation(final MyScriptParser.AggregateOperationContext ctx) {
        switch (ctx.getText()) {
        case "count":
            return AggregateFunctions.count();
        case "average":
            return AggregateFunctions.average();
        case "sum":
            return AggregateFunctions.sum();
        case "min":
            return AggregateFunctions.min();
        case "max":
            return AggregateFunctions.max();
        default:
            throw new ScriptRuntimeException("Undefined aggregate function '" + ctx.getText() + "'.");
        }
    }
}
