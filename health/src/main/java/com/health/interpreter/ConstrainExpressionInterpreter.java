package com.health.interpreter;

import java.util.function.Function;

import com.health.Record;
import com.health.Table;
import com.health.operations.Constraints;
import com.health.operations.functions.ConstrainFunctions;
import com.health.script.MyScriptParser.ConditionContext;
import com.health.script.MyScriptParser.ConditionalExpressionContext;
import com.health.script.MyScriptParser.ConstrainExpressionContext;
import com.health.script.runtime.Context;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.Value;
import com.health.script.runtime.WrapperValue;

/**
 * Represents an interpreter for constraining expressions.
 */
public final class ConstrainExpressionInterpreter extends TableExpressionInterpreter {
    /**
     * Creates a new instance of {@link ConstrainExpressionInterpreter} with the
     * given context and expressionVisitor.
     *
     * @param context
     *            the context.
     * @param expressionVisitor
     *            the expressionVisitor.
     */
    protected ConstrainExpressionInterpreter(
            final Context context,
            final ExpressionValueVisitor expressionVisitor) {
        super(context, expressionVisitor);
    }

    /**
     * Interprets the given ConstrainExpressionContext and returns the resulting
     * value.
     *
     * @param ctx
     *            the constrain expression context.
     * @return the value resulting from evaluating the expression.
     */
    public Value interpret(final ConstrainExpressionContext ctx) {
        String tableIdent = ctx.table.getText();

        Table table = lookupTable(tableIdent);

        Function<Record, Boolean> predicate = createPredicateFromConditionalExpression(
                ctx.conditionalExpression(),
                table,
                tableIdent);

        return new WrapperValue<Table>(Constraints.constrain(predicate, table));
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

        Value value = this.getExpressionVisitor().visit(ctx.expression());

        switch (ctx.comparisonOperator().getText()) {
        case "=":
            return (record) -> ConstrainFunctions.equal(record.getValue(column), value);
        case "<":
            return (record) -> ConstrainFunctions.smaller(record.getValue(column), value);
        case "<=":
            return (record) -> ConstrainFunctions.smallerEq(record.getValue(column), value);
        case ">":
            return (record) -> ConstrainFunctions.greater(record.getValue(column), value);
        case ">=":
            return (record) -> ConstrainFunctions.greaterEq(record.getValue(column), value);
        default:
            throw new ScriptRuntimeException(
                    "Encountered unknown comparison operator '" + ctx.comparisonOperator().getText() + "'.");
        }
    }
}
