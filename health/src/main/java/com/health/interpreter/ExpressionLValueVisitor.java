package com.health.interpreter;

import java.util.Objects;

import com.health.script.MyScriptBaseVisitor;
import com.health.script.MyScriptParser;
import com.health.script.runtime.Context;
import com.health.script.runtime.LValue;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.Value;

public final class ExpressionLValueVisitor extends MyScriptBaseVisitor<LValue> {
    private final Context context;
    private final ExpressionValueVisitor expressionVisitor;

    public ExpressionLValueVisitor(final Context context, final ExpressionValueVisitor expressionVisitor) {
        Objects.requireNonNull(context);

        this.context = context;
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public LValue visitLookupExpression(final MyScriptParser.LookupExpressionContext ctx) {
        return context.lookup(ctx.IDENTIFIER().getText());
    }

    @Override
    public LValue visitParenthesizedExpression(final MyScriptParser.ParenthesizedExpressionContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public LValue visitMemberAccessExpression(final MyScriptParser.MemberAccessExpressionContext ctx) {
        // Evaluate the object to be accessed
        Value obj = expressionVisitor.visit(ctx.primaryExpression());

        if (obj == null) {
            throw new ScriptRuntimeException("Object reference not set to an instance of an object.");
        }

        // Get the l-value of the member being accessed
        return obj.getMember(ctx.IDENTIFIER().getText());
    }
}
