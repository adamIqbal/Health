package com.health.interpreter;

import java.util.function.Function;

import com.health.Record;
import com.health.Table;
import com.health.operations.Constraints;
import com.health.script.MyScriptParser.ConstrainExpressionContext;
import com.health.script.runtime.Context;
import com.health.script.runtime.Value;
import com.health.script.runtime.WrapperValue;

/**
 * Represents an interpreter for constraining expressions.
 */
public final class ConstrainExpressionInterpreter extends BaseExpressionInterpreter {
    private ConditionalExpressionInterpreter conditionalInterpreter;

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

        this.conditionalInterpreter = new ConditionalExpressionInterpreter(context);
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

        Table table = BaseExpressionInterpreter.lookupTable(this.getContext(), tableIdent);

        Function<Record, Boolean> predicate = this.conditionalInterpreter.createPredicateFromConditionalExpression(
                ctx.conditionalExpression(),
                table,
                tableIdent);

        return new WrapperValue<Table>(Constraints.constrain(predicate, table));
    }
}
