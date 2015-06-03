package com.health.interpreter;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.health.AggregateFunctions;
import com.health.Table;
import com.health.operations.Chunk;
import com.health.script.MyScriptBaseVisitor;
import com.health.script.MyScriptParser;
import com.health.script.runtime.BooleanValue;
import com.health.script.runtime.Context;
import com.health.script.runtime.LValue;
import com.health.script.runtime.NullValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.ScriptDelegate;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.TableValue;
import com.health.script.runtime.Value;

public final class ExpressionValueVisitor extends MyScriptBaseVisitor<Value> {
    private Context context;
    private ExpressionLValueVisitor lValueVisitor;

    public ExpressionValueVisitor(final Context context) {
        Objects.requireNonNull(context);

        this.context = context;
    }

    public void setLValueVisitor(ExpressionLValueVisitor visitor) {
        this.lValueVisitor = visitor;
    }

    @Override
    public Value visitBoolLiteral(final MyScriptParser.BoolLiteralContext ctx) {
        if (ctx.TRUE() != null) {
            return new BooleanValue(true);
        } else {
            return new BooleanValue(false);
        }
    }

    @Override
    public Value visitNumberLiteral(final MyScriptParser.NumberLiteralContext ctx) {
        return new NumberValue(Double.parseDouble(ctx.NUMBER().getText()));
    }

    @Override
    public Value visitStringLiteral(final MyScriptParser.StringLiteralContext ctx) {
        String text = ctx.STRING().getText();

        // The string should be longer than two characters because of the quote
        // characters
        assert text.length() >= 2;

        // Strip the quotes
        text = text.substring(1, text.length() - 1);

        return new StringValue(text);
    }

    @Override
    public Value visitNullLiteral(final MyScriptParser.NullLiteralContext ctx) {
        return new NullValue();
    }

    @Override
    public Value visitLookupExpression(final MyScriptParser.LookupExpressionContext ctx) {
        return this.lValueVisitor.visit(ctx).get();
    }

    @Override
    public Value visitParenthesizedExpression(final MyScriptParser.ParenthesizedExpressionContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public Value visitMemberAccessExpression(final MyScriptParser.MemberAccessExpressionContext ctx) {
        return this.lValueVisitor.visit(ctx).get();
    }

    @Override
    public Value visitInvocationExpression(final MyScriptParser.InvocationExpressionContext ctx) {
        Value value = super.visit(ctx.primaryExpression());

        if (!(value instanceof ScriptDelegate)) {
            throw new ScriptRuntimeException("Tried to invoke expression that was not a function.");
        }

        ScriptDelegate delegate = (ScriptDelegate) value;

        // Create argument list
        Value[] args = evaluateArgumentList(ctx.argumentList());

        return delegate.invoke(args);
    }

    @Override
    public Value visitAssignmentExpression(final MyScriptParser.AssignmentExpressionContext ctx) {
        LValue left = this.lValueVisitor.visit(ctx.primaryExpression());
        Value right = super.visit(ctx.expression());

        left.set(right);

        return right;
    }

    @Override
    public Value visitChunkExpression(final MyScriptParser.ChunkExpressionContext ctx) {
        String tableIdent = ctx.tableIdent.getText();
        String columnIdent = ctx.columnIdent.getText();

        LValue tableVar = context.lookup(tableIdent);

        if (!TableValue.getStaticType().isAssignableFrom(tableVar.getType())) {
            throw new ScriptRuntimeException("Chunking can only be performed on a table instance.");
        }

        Table table = ((TableValue) tableVar.get()).getValue();

        if (table.getColumn(columnIdent) == null) {
            throw new ScriptRuntimeException(String.format("Table '%s' does not define a column named '%s'.",
                    tableIdent, columnIdent));
        }

        Map<String, AggregateFunctions> aggregateFunctions = evaluateAggragateOperations(ctx.columnAggregateOperation());

        if (ctx.periodSpecifier() != null) {
            Period period = evaluatePeriod(ctx.periodSpecifier().period());

            return new TableValue(Chunk.chunkByTime(table, columnIdent, aggregateFunctions, period));
        } else {
            return new TableValue(Chunk.chunkByString(table, columnIdent, aggregateFunctions));
        }
    }

    private Period evaluatePeriod(final MyScriptParser.PeriodContext ctx) {
        if (ctx.singularTimeUnit() != null) {
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
        } else {
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
    }

    private Map<String, AggregateFunctions> evaluateAggragateOperations(
            final List<MyScriptParser.ColumnAggregateOperationContext> columnAggregateOperation) {
        Map<String, AggregateFunctions> aggregateFunctions = new HashMap<String, AggregateFunctions>();

        for (MyScriptParser.ColumnAggregateOperationContext ctx : columnAggregateOperation) {
            aggregateFunctions.put(ctx.IDENTIFIER().getText(), evaluateAggragateOperation(ctx.aggregateOperation()));
        }

        return aggregateFunctions;
    }

    private AggregateFunctions evaluateAggragateOperation(final MyScriptParser.AggregateOperationContext ctx) {
        switch (ctx.getText()) {
        case "count":
            // FIXME: Add count aggregate
            return AggregateFunctions.Average;
        case "average":
            return AggregateFunctions.Average;
        case "sum":
            return AggregateFunctions.Sum;
        case "min":
            return AggregateFunctions.Min;
        case "max":
            return AggregateFunctions.Max;
        default:
            throw new ScriptRuntimeException(String.format("Unknown aggregate function '%s'.", ctx.getText()));
        }
    }

    /**
     * Evaluates the arguments for a given {@link ArgumentList}.
     *
     * @param arguments
     *            the argument list to evaluate.
     * @param context
     *            the runtime environment.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur.
     */
    private Value[] evaluateArgumentList(final MyScriptParser.ArgumentListContext ctx) throws ScriptRuntimeException {
        // Create a list for the arguments
        List<Value> values = new ArrayList<Value>();

        if (ctx != null) {
            // Evaluate the arguments in the argument list and add them to the
            // list
            this.evaluateArguments(ctx, values);
        }

        // Convert the list to an array
        Value[] args = new Value[values.size()];
        values.toArray(args);

        return args;
    }

    private void evaluateArguments(final MyScriptParser.ArgumentListContext ctx, final List<Value> values)
            throws ScriptRuntimeException {
        // Evaluate the preceding arguments
        if (ctx.argumentList() != null) {
            this.evaluateArguments(ctx.argumentList(), values);
        }

        // Evaluate the argument and add it to the list
        values.add(super.visit(ctx.expression()));
    }
}
