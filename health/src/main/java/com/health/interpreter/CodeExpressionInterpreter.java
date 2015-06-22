package com.health.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.health.Record;
import com.health.Table;
import com.health.script.MyScriptParser;
import com.health.script.runtime.Context;
import com.health.script.runtime.EventListValue;
import com.health.script.runtime.Value;

/**
 * Represents an interpreter for coding expressions.
 */
public final class CodeExpressionInterpreter extends BaseExpressionInterpreter {
    private ConditionalExpressionInterpreter conditionalInterpreter;

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

        this.conditionalInterpreter = new ConditionalExpressionInterpreter(context);
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
        Table table = BaseExpressionInterpreter.lookupTable(this.getContext(), tableIdent);

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
                this.conditionalInterpreter.createPredicateFromConditionalExpression(
                        ctx.conditionalExpression(),
                        table,
                        tableIdent));
    }
}
