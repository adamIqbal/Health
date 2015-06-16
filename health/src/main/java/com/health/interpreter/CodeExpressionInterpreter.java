package com.health.interpreter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.operations.functions.ConstrainFunctions;
import com.health.script.MyScriptParser;
import com.health.script.MyScriptParser.ConditionContext;
import com.health.script.MyScriptParser.ConditionalExpressionContext;
import com.health.script.MyScriptParser.ExpressionContext;
import com.health.script.runtime.Context;
import com.health.script.runtime.DateValue;
import com.health.script.runtime.EventListValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;

/**
 * Represents an interpreter for coding expressions.
 */
public final class CodeExpressionInterpreter extends TableExpressionInterpreter {
    /**
     * Creates a new instance of {@link ChunkExpressionInterpreter} with the
     * given context and expressionVisitor.
     *
     * @param context
     *            the context.
     * @param expressionVisitor
     *            the expressionVisitor.
     */
    protected CodeExpressionInterpreter(
            final Context context,
            final ExpressionValueVisitor expressionVisitor) {
        super(context, expressionVisitor);
    }

    /**
     * Interprets the given CodeExpressionContext and returns the resulting
     * value.
     *
     * @param ctx
     *            the code expression context.
     * @return the value resulting from evaluating the expression.
     */
    public Value interpret(final MyScriptParser.CodeExpressionContext ctx) {
        String tableIdent = ctx.table.getText();
        Table table = lookupTable(tableIdent);

        Map<String, Function<Record, Boolean>> codes = evaluateCodeList(ctx.codeList(), table, tableIdent);

        return new EventListValue(com.health.operations.Code.makeEventList(table, codes));
    }

    private Map<String, Function<Record, Boolean>> evaluateCodeList(
            final MyScriptParser.CodeListContext ctx,
            final Table table,
            final String tableIdent) {
        Map<String, Function<Record, Boolean>> codes = new HashMap<String, Function<Record, Boolean>>();

        evaluateCodeList(ctx, table, tableIdent, codes);

        return codes;
    }

    private void evaluateCodeList(
            final MyScriptParser.CodeListContext ctx,
            final Table table,
            final String tableIdent,
            final Map<String, Function<Record, Boolean>> codes) {
        if (ctx.codeList() != null) {
            evaluateCodeList(ctx.codeList(), table, tableIdent, codes);
        }

        evaluateCode(ctx.code(), table, tableIdent, codes);
    }

    private void evaluateCode(
            final MyScriptParser.CodeContext ctx,
            final Table table,
            final String tableIdent,
            final Map<String, Function<Record, Boolean>> codes) {
        codes.put(ctx.IDENTIFIER().getText(),
                createPredicateFromConditionalExpression(ctx.conditionalExpression(), table, tableIdent));
    }

    private Function<Record, Boolean> createPredicateFromConditionalExpression(
            final ConditionalExpressionContext ctx,
            final Table table,
            final String tableIdent) {
        if (ctx.booleanOperator() != null) {
            return createPredicateFromBinaryConditionalExpression(ctx, table, tableIdent);
        } else if (ctx.conditionalExpression() != null) {
            return createPredicateFromConditionalExpression(ctx.conditionalExpression(), table, tableIdent);
        } else {
            return createPredicateFromCondition(ctx.condition(), table, tableIdent);
        }
    }

    private Function<Record, Boolean> createPredicateFromBinaryConditionalExpression(
            final ConditionalExpressionContext ctx,
            final Table table,
            final String tableIdent) {
        Function<Record, Boolean> left = createPredicateFromConditionalExpression(ctx.conditionalExpression(), table,
                tableIdent);
        Function<Record, Boolean> right = createPredicateFromCondition(ctx.condition(), table, tableIdent);

        if (ctx.booleanOperator().getText().equals("and")) {
            return (record) -> left.apply(record) && right.apply(record);
        } else if (ctx.booleanOperator().getText().equals("or")) {
            return (record) -> left.apply(record) || right.apply(record);
        } else {
            throw new ScriptRuntimeException(
                    "Encountered unknown boolean operator '" + ctx.booleanOperator().getText() + "'.");
        }
    }

    private Function<Record, Boolean> createPredicateFromCondition(
            final ConditionContext ctx,
            final Table table,
            final String tableIdent) {
        String column = ctx.column.getText();

        verifyHasColumn(table, tableIdent, column);

        Context context = this.getContext();
        ExpressionContext expr = ctx.expression();

        switch (ctx.comparisonOperator().getText()) {
        case "==":
        case "=":
            return evaluate(context, expr, (record, value) -> ConstrainFunctions.equal(record.getValue(column), value));
        case "!=":
            return evaluate(context, expr, (record, value) -> !ConstrainFunctions.equal(record.getValue(column), value));
        case "<":
            return evaluate(context, expr,
                    (record, value) -> ConstrainFunctions.smaller(record.getValue(column), value));
        case "<=":
            return evaluate(context, expr,
                    (record, value) -> ConstrainFunctions.smallerEq(record.getValue(column), value));
        case ">":
            return evaluate(context, expr,
                    (record, value) -> ConstrainFunctions.greater(record.getValue(column), value));
        case ">=":
            return evaluate(context, expr,
                    (record, value) -> ConstrainFunctions.greaterEq(record.getValue(column), value));
        default:
            throw new ScriptRuntimeException(
                    "Encountered unknown comparison operator '" + ctx.comparisonOperator().getText() + "'.");
        }
    }

    private static Function<Record, Boolean> evaluate(
            final Context context,
            final ExpressionContext expr,
            final BiFunction<Record, Value, Boolean> function) {
        return (record) -> {
            enterScope(context, record);
            Value value = new ExpressionValueVisitor(context).visit(expr);
            exitScope(context, record);
            boolean result = function.apply(record, value);

            return result;
        };
    }

    private static void enterScope(final Context context, final Record record) {
        Table table = record.getTable();
        List<Object> values = record.getValues();

        for (int i = 0; i < values.size(); i++) {
            Column column = table.getColumn(i);
            Value value = wrapValue(values.get(i));

            if (value != null) {
                context.declareLocal(column.getName(), value.getType(), value);
            } else {
                context.declareLocal(column.getName(), Value.getStaticType(), null);
            }
        }
    }

    private static void exitScope(final Context context, final Record record) {
        Table table = record.getTable();
        List<Object> values = record.getValues();

        for (int i = 0; i < values.size(); i++) {
            Column column = table.getColumn(i);
            context.removeLocal(column.getName());
        }
    }

    private static Value wrapValue(final Object value) {
        if (value instanceof Double) {
            return new NumberValue((Double) value);
        } else if (value instanceof String) {
            return new StringValue((String) value);
        } else if (value instanceof LocalDate) {
            return new DateValue((LocalDate) value);
        } else if (value != null) {
            throw new IllegalArgumentException("Invalid value type, must be Double, String or LocalDate.");
        } else {
            return null;
        }
    }
}
