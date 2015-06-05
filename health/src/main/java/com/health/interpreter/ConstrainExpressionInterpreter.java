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
import com.health.script.runtime.TableValue;
import com.health.script.runtime.Value;

public final class ConstrainExpressionInterpreter extends TableExpressionInterpreter {
    protected ConstrainExpressionInterpreter(
            final Context context,
            final ExpressionValueVisitor expressionVisitor) {
        super(context, expressionVisitor);
    }

    public Value interpret(final ConstrainExpressionContext ctx) {
        String tableIdent = ctx.table.getText();

        Table table = lookupTable(tableIdent);

        Function<Record, Boolean> predicate = evaluateConditionalExpression(
                ctx.conditionalExpression(),
                table,
                tableIdent);

        return new TableValue(Constraints.constrain(predicate, table));
    }

    private Function<Record, Boolean> evaluateConditionalExpression(
            final ConditionalExpressionContext ctx,
            final Table table,
            final String tableIdent) {
        if (ctx.booleanOperator() != null) {
            Function<Record, Boolean> left = evaluateConditionalExpression(ctx.conditionalExpression(), table,
                    tableIdent);
            Function<Record, Boolean> right = evaluateCondition(ctx.condition(), table, tableIdent);

            if (ctx.booleanOperator().getText().equals("and")) {
                return (record) -> left.apply(record) && right.apply(record);
            } else if (ctx.booleanOperator().getText().equals("or")) {
                return (record) -> left.apply(record) || right.apply(record);
            } else {
                assert false;

                throw new ScriptRuntimeException("??");
            }
        } else if (ctx.conditionalExpression() != null) {
            return evaluateConditionalExpression(ctx.conditionalExpression(), table, tableIdent);
        } else {
            return evaluateCondition(ctx.condition(), table, tableIdent);
        }
    }

    private Function<Record, Boolean> evaluateCondition(
            final ConditionContext ctx,
            final Table table,
            final String tableIdent) {
        String column = ctx.column.getText();

        verifyHasColumn(table, tableIdent, column);

        Value value = this.expressionVisitor.visit(ctx.expression());

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
            assert false;

            return null;
        }
    }
}
